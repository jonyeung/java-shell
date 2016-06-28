package driver;

import java.util.Arrays;

/**
 * This class has methods that check if user input is valid and can interpret
 * user input for use in other classes
 */
public class Interpreter {

  // The valid commands JShell can run
  private final static String commands[] = {"mkdir", "cd", "ls", "pwd", "pushd",
      "popd", "history", "cat", "echo", "man"};

  // The maximum and minimum number of arguments of a command corresponding to
  // the commands array
  private final static int maxArgs[] = {-1, 1, -1, 0, 1, 0, 1, -1, 3, 1};
  private final static int minArgs[] = {1, 1, 0, 0, 1, 0, 0, 1, 1, 1};

  /**
   * Gets input and separates the input into an array using the separator
   * 
   * @param input The input that was given from the user
   * @param separator Split the input by the separator
   * @return String[] An array of strings that are the words
   */
  private static String[] inputToArray(String input, String separator) {

    // Split the input up by space.
    String words[] = input.split(separator);

    String inputWords[] = new String[words.length];
    int numInputs = 0;

    // If extra spaces are in input, remove them. Go through each split word
    for (int i = 0; i < words.length; i++) {

      // If the word is not a space then add it to the input array
      if (words[i].length() != 0) {
        inputWords[numInputs] = words[i];
        numInputs++;
      }
    }

    // Return only the values in the array that words
    return Arrays.copyOfRange(inputWords, 0, numInputs);
  }

  /**
   * Gets the command and returns an array without spaces included.
   * 
   * @param command A command that was input from the user in JShell
   * @return String[] An array of strings that are the commands words
   * @throws CommandException
   */
  public static String[] commandToArray(String command)
      throws CommandException {

    String words[] = inputToArray(command, " ");
    // If the first word is echo, then inputToArray would have broken up the
    // string into words. The other method keeps anything in double quotes as
    // one string
    if (words[0].equals("echo")) {
      words = echoInputToArray(command);
    }
    return words;
  }

  /**
   * Gets a file path and splits it according to '/' character
   * 
   * @param filepath A file path to a file or directory in the file system
   * @return String[] An array of strings that are individual directory or files
   */
  public static String[] filepathToArray(String filepath) {

    return inputToArray(filepath, "/");
  }

  /**
   * If the user entered a double quote while using echo, then there must be
   * text with double quotes in between. This will keep the text as one string
   * 
   * @param input The input from the user
   * @return String[] An array of strings separated properly
   * @throws CommandException
   */
  private static String[] echoInputToArray(String input)
      throws CommandException {

    int startIndex = 0;
    int endIndex = 0;
    int count = 0;
    boolean notFound = true;

    // Find the location of the double quotes
    while (count < input.length() && notFound) {
      if (input.charAt(count) == '"') {
        if (startIndex == 0) {
          startIndex = count;
        } else {
          endIndex = count;
          notFound = false;
        }
      }
      count++;
    }

    // If closing quote is not found, throw exception
    if (notFound == true) {
      // Raise exception if user does not have a closing double quote
      throw new CommandException(
          "Quote does not end. See the manual " + "for echo.");
    }

    // Get the words between the start and end quote
    String quote = input.substring(startIndex + 1, endIndex);

    // Get the words of everything other then the string in quote
    String restOfInput =
        input.substring(0, startIndex) + input.substring(endIndex + 1);
    String words[] = inputToArray(restOfInput, " ");

    String newInput[] = new String[words.length + 1];
    newInput[0] = words[0];
    newInput[1] = quote;

    // If the quote is not on the last word from input, then add words from
    // input to newInput
    for (int i = 2; i < newInput.length; i++) {
      newInput[i] = words[i - 1];
    }
    return newInput;
  }

  /**
   * Gets the input from the JShell and checks whether it is valid or not.
   * 
   * @param input The string entered by the user
   * @return boolean If input is a valid command
   * @throws CommandException
   */
  public static boolean validInput(String input) throws CommandException {

    // Separate input into an array using whitespace between each word
    String inputWords[] = commandToArray(input);
    boolean result = false;

    // Throw exception if no inputWords[0]
    if (inputWords.length != 0) {
      // Check the first word is valid
      int commandPosition = validCommand(inputWords[0]);
      if (commandPosition != -1) {
        // Check that the correct number of arguments is given
        result = validNumberArguments(commandPosition, inputWords);
      }
    }
    return result;
  }

  /**
   * Gets the first word from the input and checks if it is a valid command. If
   * valid then it returns the index of the command name in commands array
   * 
   * @param command The first word entered
   * @return int Index of the command name
   * @throws CommandException
   */
  private static int validCommand(String command) throws CommandException {

    // Check if the 1st word input is a valid command by comparing it to
    // the array of commands
    int count = 0;
    boolean notFound = true;

    // Loop through the commands array to see if the user's inputted command
    // is in it
    while (count < commands.length && notFound) {

      // Convert the command to lowercase for more command flexibility
      if (command.toLowerCase().equals(commands[count])) {
        notFound = false;
      } else {
        count++;
      }
    }
    int result;
    if (notFound) {

      // Raise exception if valid command is not given
      throw new CommandException(command + " is not a valid command name.");
    } else {

      result = count;
    }
    return result;
  }

  /**
   * Checks if the number of arguments entered is valid
   * 
   * @param index Index of the command name
   * @param input Each word entered in an array
   * @return boolean Correct number of arguments
   * @throws CommandException
   */
  private static boolean validNumberArguments(int index, String[] input)
      throws CommandException {

    int numArgs = input.length - 1;
    boolean result = false;

    // Check that in the input array, the correct number of arguments
    // was given
    if (minArgs[index] <= numArgs
        && (numArgs <= maxArgs[index] || maxArgs[index] == -1)) {
      result = true;
    } else {
      // Throw exception if user did not input the correct number of arguments
      throw new CommandException("Please supply valid arguments for the "
          + commands[index] + " command.\nSee the manual of " + commands[index]
          + " for usage information.");
    }
    return result;
  }

  /**
   * Checks if the file name is valid. It is valid if no special characters are
   * used in the name and if a file with the same name in the parent directory
   * does not exist
   * 
   * @param fileName The file name to check
   * @param parentDir The directory that the new file will be made in
   * @return boolean Whether the file name is valid
   */
  public static boolean checkFileName(String fileName, Directory parentDir) {

    // Check that no current file in the parent directory has the same name
    boolean result = !parentDir.fileInDirectory(fileName);

    // Check that the dirName doesn't contain special characters
    int i = 0;
    while (i < fileName.length() && result) {
      if (!Character.isLetterOrDigit(fileName.charAt(i))) {
        result = false;
      }
      i++;
    }

    return result;
  }
}
