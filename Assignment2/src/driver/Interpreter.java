package driver;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class has methods that check if user input is valid and can interpret
 * user input for use in other classes
 */
public class Interpreter {

  // The valid commands JShell can run
  private final static String commands[] = {"mkdir", "cd", "ls", "pwd", "pushd",
      "popd", "history", "cat", "echo", "man", "mv", "cp", "curl", "grep", "!",
      "exit"};

  // The maximum and minimum number of arguments of a command corresponding to
  // the commands array
  private final static int maxArgs[] =
      {-1, 1, -1, 0, 1, 0, 1, -1, 3, 1, 2, 2, 1, -1, 1, 0};
  private final static int minArgs[] =
      {1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 2, 2, 1, 2, 1, 0};

  /**
   * Gets input and separates the input into an array using the separator
   * 
   * @param input The input that was given from the user
   * @param separator Split the input by the separator
   * @return String[] An array of strings that are the words wanted
   * @throws CommandException
   */
  private static String[] inputToArray(String input, char separator)
      throws CommandException {

    // Split the input up by the separator.
    ArrayList<String> stringArray = separateInput(input, separator);

    // turn array list into an array
    String[] words = new String[stringArray.size()];
    words = stringArray.toArray(words);

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
   * Gets input and separates the input into an array list using the separator.
   * 
   * @param input The input that was given from the user
   * @param separator Split the input by the separator
   * @return ArrayList An array list of strings that are the words wanted
   * @throws CommandException
   */
  private static ArrayList<String> separateInput(String input, char separator)
      throws CommandException {

    ArrayList<String> returnStrings = new ArrayList<String>();
    int startIndex = 0;
    boolean inQuote = false;

    // loop through the input and separate the string based on the separator
    for (int i = 0; i < input.length(); i++) {

      // if index i is not in the middle of a quote, and we found a separator
      // char then we want to get the word
      if (input.charAt(i) == separator && !inQuote) {
        returnStrings.add(input.substring(startIndex, i));
        startIndex = i + 1;

        // if index i found the " char then we want to include everything after
        // it as a whole until we find another " char
      } else if (input.charAt(i) == '\"' && !inQuote) {
        inQuote = true;
        startIndex = i + 1;

      } else if (input.charAt(i) == '\"' && inQuote) {
        inQuote = false;
        returnStrings.add(input.substring(startIndex, i));
        startIndex = i + 1;

      }
    }
    // if the number " characters do not match up, then there is an error in
    if (inQuote) {
      throw new CommandException("Quote does not end.");
    } else {
      // add the last word
      returnStrings.add(input.substring(startIndex, input.length()));
    }
    return returnStrings;
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

    // Special exception for "!" command, since it has no spaces
    if (command.startsWith("!")) {
      command = command.substring(0, 1) + " "
          + command.substring(1, command.length());
    }
    return inputToArray(command, ' ');
  }

  /**
   * Gets a file path and splits it according to '/' character
   * 
   * @param filepath A file path to a file or directory in the file system
   * @return String[] An array of strings that are individual directory or files
   * @throws CommandException
   */
  public static String[] filepathToArray(String filepath)
      throws CommandException {

    return inputToArray(filepath, '/');
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
    int commandPosition = 0;

    // Throw exception if no inputWords[0]
    if (inputWords.length != 0) {
      // Check the first word is valid
      commandPosition = validCommand(inputWords[0]);
      // Check the placement of chevrons in the input is correct
      result = usesChevrons(inputWords);
    }
    // If the input uses chevrons, then change inputWords since we only want to
    // check if everything upto the chevrons in the command is valid
    if (result) {
      inputWords = Arrays.copyOfRange(inputWords, 0, inputWords.length - 2);
    }

    // Check that the correct number of arguments is given
    result = validNumberArguments(commandPosition, inputWords);

    return result;
  }

  /**
   * Gets the first word from the input and checks if it is a valid command. If
   * valid then it returns the index of the command name in commands array
   * 
   * @param command The first word entered
   * @return int Index of the command name
   * @throws CommandException Command name is not valid
   */
  private static int validCommand(String command) throws CommandException {

    // Check if the 1st word input is a valid command by comparing it to
    // the array of commands
    int count = 0;
    boolean notFound = true;

    // Loop through the commands array to see if the user's inputed command
    // is in it
    while (count < commands.length && notFound) {

      // Convert the command to lower case for more command flexibility
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
   * @throws CommandException If the wrong number of arguments are given
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
   * If chevrons are used, return true, otherwise return false. If chevrons are
   * in the wrong location or there are more than one, then it will throw an
   * exception
   * 
   * @param input Each word entered in an array
   * @return boolean If the input uses chevrons
   * @throws CommandException If there are multiple chevrons or in wrong index
   */
  private static boolean usesChevrons(String[] input) throws CommandException {

    int i = 0;
    boolean result = false;

    // Loop through each string in input and check if any are single or double
    // chevrons
    while (i < input.length && !result) {
      if (input[i].equals(">") || input[i].equals(">>")) {
        // If the index is not at the second last position, then the chevrons
        // are in the wrong location
        if (i != input.length - 2) {
          throw new CommandException(
              "The placement of " + input[i] + " is not correct.");
        } else {
          result = true;
        }
      }
      i++;
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
   * @throws CommandException
   */
  public static boolean checkFileName(String fileName, Directory parentDir)
      throws CommandException {

    // Check that no current file in the parent directory has the same name
    boolean result = !parentDir.fileInDirectory(fileName);

    // separate the fileName by '.' character
    String[] name = inputToArray(fileName, '.');

    // If name is empty then fileName only contained '.' characters and is not
    // valid
    if (name.length == 0) {
      result = false;
    }

    // Go through each index in name and make sure each are valid
    int j = 0;
    while (j < name.length && result) {
      String file = name[j];
      // Check that the file doesn't contain special characters
      int i = 0;
      while (i < file.length() && result) {
        if (!Character.isLetterOrDigit(file.charAt(i))) {
          result = false;
        }
        i++;
      }
      j++;
    }

    return result;
  }
}
