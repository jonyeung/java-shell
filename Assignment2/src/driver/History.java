package driver;

import java.util.ArrayList;
import driver.CommandException;

/**
 * This class consists exclusively of static methods for History
 */
public class History {

  private static ArrayList<String> historyFile = new ArrayList<String>();
  private static int numLines = 0;

  /**
   * Gets input and adds it to the history
   * 
   * @param input The input that was given from the user
   */
  public static void addToHistory(String input) {

    // Increment the number of lines in the file
    numLines++;

    String appendLine = Integer.toString(numLines) + ". " + input;

    // add the input line to the history file
    historyFile.add(appendLine);
  }

  /**
   * Returns the past commands entered.
   * 
   * @param commandArgs The number of past commands the user wants
   * @return String The output of history
   */
  public static String executeHistory(String[] commandArgs)
      throws CommandException {

    String output = "";
    // If an argument is given, then check if it is a number. If so return the
    // n-th past commands.
    if (commandArgs != null) {
      int numPastCommands;
      boolean success = true;
      try {
        numPastCommands = Integer.parseInt(commandArgs[0]);
      } catch (Exception e) {
        success = false;
        throw new CommandException("Argument given is not an integer");
      }

      if (success) {
        output = printHistory(numPastCommands);
      }

    } else {
      // If no argument is given then print all past commands
      output = printHistory(numLines);
    }
    return output;
  }

  /**
   * Gets the number of lines of past commands that need to be printed and
   * returns them
   * 
   * @param lastLines The number of past commands that the user wants to see
   * @return String The lines that we want to return
   * @throws CommandException
   */
  private static String printHistory(int lastLines) throws CommandException {

    int startLineIndex = numLines - lastLines;
    String output = "";

    if (startLineIndex >= 0 && lastLines > 0) {

      // Loop through each line starting at startLineIndex and print them
      for (int i = startLineIndex; i < numLines; i++) {
        output += historyFile.get(i) + "\n";
      }
    } else {

      // Throw the command exception if user enters invalid history number
      throw new CommandException(
          "History for the past " + lastLines + " command(s) does not exist.");
    }
    return output.substring(0, output.length() - 1);
  }

  /**
   * Delete all previous commands from history
   */
  public static void resetHistory() {

    historyFile.clear();
    numLines = 0;
  }

  /**
   * 
   * @param commandPosition The command
   * @return String The command to be returned from historyFile
   * @throws CommandException
   */
  public static String recallExactCommand(String commandArg)
      throws CommandException {
    String commandToRun = "";
    int commandPosition = 0;
    Boolean infLoop = false;

    // Check if arguments are valid for "!" command
    if (checkExactExecute(commandArg) == true) {
      commandPosition = Integer.parseInt(commandArg);
      commandToRun = historyFile.get(commandPosition - 1).substring(3);

      // Cannot re-execute command starting with "!"
      if (commandToRun.startsWith("!")) {
        try {
          Integer.parseInt(commandToRun.substring(1));
          infLoop = true;
        } catch (Exception e) {
          // Go through appropriate error messages if arguments are valid
          checkExactExecute(commandToRun.substring(1));
        }
      }

      // Infinite loop if asked to re-run a "!" command.
      if (infLoop == true) {
        throw new CommandException("Cannot re-execute command at position "
            + Integer.toString(commandPosition)
            + " (infinite loop).\nPlease see the manual for the \"!\" "
            + "command.");
      }
    }

    return commandToRun;
  }

  private static Boolean checkExactExecute(String commandArg)
      throws CommandException {
    String commandToRun = "";
    int commandPosition = 0;

    try {
      commandPosition = Integer.parseInt(commandArg);
    } catch (Exception e) {
      throw new CommandException("Please input a number.");
    }
    // Command position cannot be less than 1
    if (commandPosition < 1) {
      throw new CommandException("Invalid history number");
    }

    // Invalid number input exception
    try {
      // Get the command to run and split the extra bit at the start
      commandToRun = historyFile.get(commandPosition - 1).substring(3);
    } catch (Exception e) {
      throw new CommandException("Please input a valid number.");
    }

    return true;
  }
}
