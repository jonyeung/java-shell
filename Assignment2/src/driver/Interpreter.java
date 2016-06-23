package driver;

import java.util.Arrays;

public class Interpreter {

  private static String commands[] = {"mkdir", "cd", "ls", "pwd", "pushd",
      "popd", "history", "cat", "echo", "man"};

  // the maximum and minimum number of arguments of a command corresponding to
  // the commands array
  private static int maxArgs[] = {-1, 1, 1, 0, 1, 0, 1, -1, 3, 1};
  private static int minArgs[] = {1, 1, 0, 0, 1, 0, 0, 1, 1, 1};

  /**
   * Gets input and separates the input into an array using the separator
   * 
   * @param input The input that was given from the user
   * @param separator Split the input by the separator
   * @return String[] An array of strings that are the words
   */
  public static String[] inputToArray(String input, String separator) {

    // split the input up by space.
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

    // returns only the values in the array that words
    return Arrays.copyOfRange(inputWords, 0, numInputs);
  }

  /**
   * Gets the command and returns an array without spaces included.
   * 
   * @param command A command that was input from the user in JShell
   * @return String[] An array of strings that are the commands words
   */
  public static String[] commandToArray(String command) {

    String words[] = inputToArray(command, " ");
    // if the first word is echo, then inputToArray would have broken up the
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
   */
  private static String[] echoInputToArray(String input) {

    int startIndex = 0;
    int endIndex = 0;
    int count = 0;
    boolean notFound = true;

    // find the location of the double quotes
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

    // if closing quote is not found throw exception
    if (notFound == true) {
      // Raise exception if user does not have a closing double quote
      try {
        throw new CommandException("Quote does not end");
      } catch (CommandException e) {
        // Print the exception message
        System.out.println(e.getMessage());
      }
    }

    // get the words between the start and end quote
    String quote = input.substring(startIndex + 1, endIndex);
    // get the words of everything other then the string in quote
    String restOfInput =
        input.substring(0, startIndex) + input.substring(endIndex + 1);
    String words[] = inputToArray(restOfInput, " ");

    String newInput[] = new String[words.length + 1];
    newInput[0] = words[0];
    newInput[1] = quote;
    // if the quote is not on the last word from input, then add words from
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
   */
  public static boolean validInput(String input) {

    // separate input into an array using whitespace between each word
    String inputWords[] = commandToArray(input);
    boolean result = false;

    // throw exception if no inputWords[0]
    if (inputWords.length != 0) {
      // checks the first word in valid
      int commandPosition = validCommand(inputWords[0]);
      if (commandPosition != -1) {
        // checks that the right number of arguments is given
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
   */
  private static int validCommand(String command) {

    // checks if the 1st word input is a valid command by comparing it to
    // the array of commands
    int count = 0;
    boolean notFound = true;
    // loop through the commands array to see if the user's inputted command
    // is in it
    while (count < commands.length && notFound) {
      // convert the command to lowercase for more command flexibility
      if (command.toLowerCase().equals(commands[count])) {
        notFound = false;
      } else {
        count++;
      }
    }
    int result;
    if (notFound) {
      // Raise exception if valid command is not given
      try {
        throw new CommandException(command + " is not a valid command name.");
      } catch (CommandException e) {
        // Print the exception message
        System.out.println(e.getMessage());
      }
      result = -1;
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
   */
  private static boolean validNumberArguments(int index, String[] input) {

    int numArgs = input.length - 1;
    boolean result = false;
    // checks that the in the input array, the correct number of arguments
    // was given
    if (minArgs[index] <= numArgs
        && (numArgs <= maxArgs[index] || maxArgs[index] == -1)) {
      result = true;
    } else {
      // Raise exception if user did not input the correct number of arguments
      try {
        throw new CommandException("Please supply valid arguments for the "
            + commands[index] + " command.\nSee the manual of "
            + commands[index] + " for usage information.");
      } catch (CommandException e) {
        // Print the exception message
        System.out.println(e.getMessage());
      }
    }
    return result;
  }
}
