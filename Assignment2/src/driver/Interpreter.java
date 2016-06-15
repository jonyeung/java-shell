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

    // if first word is echo, then call combine strings



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
    return inputToArray(command, " ");
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



  public static String[] combineStrings(String[] input) {

    int startQuoteIndex = 1;
    int endQuoteIndex = 1;

    int index = 0;
    boolean notFound = true;

    // loop through each input and check if any word ends with a double quote.
    while (index < input.length && notFound) {

      int lastCharIndex = input[index].length() - 1;
      char lastChar = input[index].charAt(lastCharIndex);

      if (lastChar == '"') {
        notFound = false;
      } else {
        index++;
      }
    }

    
    
    if (notFound == false) {
      // Concatenate the word at index 1 with all the words between endQuoteIndex
      for (int i = startQuoteIndex; i <= index; i++) {
        //TODO work on this
      }
    }




    String result[] = {};
    return result;
  }



  /**
   * Gets the input from the JShell and checks whether it is valid or not.
   * 
   * @param input The string entered by the user
   * @return boolean If input is a valid command
   */
  public static boolean validInput(String input) {
    // separate the input into an array using the whitespace in between each
    // word
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
    // checks if the first word inputed is a valid command by comparing it to
    // the array of commands
    int count = 0;
    boolean notFound = true;
    while (count < commands.length && notFound) {
      if (command.equals(commands[count])) {
        notFound = false;
      } else {
        count++;
      }
    }
    int result;
    if (notFound) {
      // TODO throw exception
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

    if (minArgs[index] <= numArgs
        && (numArgs <= maxArgs[index] || maxArgs[index] == -1)) {
      result = true;
    }
    return result;
  }



}
