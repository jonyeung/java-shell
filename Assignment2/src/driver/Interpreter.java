package driver;

import java.util.Arrays;

public class Interpreter {

  private static String commands[] = {"exit", "mkdir", "cd", "ls", "pwd",
      "pushd", "popd", "history", "cat", "echo", "man"};

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

  /**
   * Gets a file path in an array and checks it is valid. A file path is valid
   * if none of the directories it references have spaces in it
   * 
   * @param filepath A file path to a file or directory in the file system
   * @return String[] An array of strings that are individual directory or files
   */
  public boolean correctFilePath(String[] filepath) {
    // not complete
    return true;
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

    // checks if the first word inputed is a valid command by comparing it to
    // the array of commands
    int count = 0;
    boolean notFound = true;
    while (count < commands.length && notFound) {
      if (inputWords[0].equals(commands[count])) {
        notFound = false;
      } else {
        count++;
      }
    }
    boolean result = !notFound;

    // if the command is valid then do the next check
    if (result) {
      // not complete
    }

    return result;
  }
}
