package driver;

import java.util.ArrayList;
import driver.CommandException;

public class History {

  private static ArrayList<String> historyFile = new ArrayList<String>();
  private static int numLines = 0;

  /**
   * Gets input and adds it to the history
   * 
   * @param input The input that was given from the user
   */
  public static void addToHistory(String input) {

    // increment the number of lines in the file
    numLines++;

    String appendLine = Integer.toString(numLines) + ". " + input;
    // add the input line to the history file
    historyFile.add(appendLine);
  }

  /**
   * Gets the number of lines of past commands that need to be printed and
   * prints them
   * 
   * @param lastLines The number of past commands that the user wants to see
   */
  public static void printHistory(int lastLines) {

    int startLineIndex = numLines - lastLines;

    if (startLineIndex >= 0) {

      // loop through each line starting at startLineIndex and print them
      for (int i = startLineIndex; i < numLines; i++) {
        System.out.println(historyFile.get(i));
      }

    } else {
      // Raise exception when user inputs an invalid history number
      try {
        throw new CommandException(
            "History for the past " + lastLines + " commands does not exist.");
      } catch (CommandException e) {
        // Print the message
        System.out.println(e.getMessage());
      }

    }

  }

  /**
   * Gets the all past commands and prints them
   */
  public static void printAllHistory() {
    printHistory(numLines);
  }
}
