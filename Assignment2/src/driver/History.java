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
   * Delete all previous commands from history
   * 
   */
  public static void resetHistory() {

    historyFile.clear();
    numLines = 0;
  }

  /**
   * Gets the number of lines of past commands that need to be printed and
   * returns them
   * 
   * @param lastLines The number of past commands that the user wants to see
   * @return String The lines that we want to return
   * @throws CommandException
   */
  public static String printHistory(int lastLines) throws CommandException {

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
   * Gets the all past commands and prints them
   * 
   * @return String All past commands
   * @throws CommandException
   */
  public static String printAllHistory() throws CommandException {

    return printHistory(numLines);
  }
}
