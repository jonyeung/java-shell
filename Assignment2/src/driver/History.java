package driver;

public class History {

  private static TextFile historyFile = new TextFile("history");
  private static int numLines = 0;

  /**
   * Gets input and adds it to the history
   * 
   * @param input The input that was given from the user
   */
  public static void addToHistory(String input) {
    // increment the number of lines in the file
    numLines++;

    String appendLine = Integer.toString(numLines) + ". " + input + "\n";
    // add the input line to the history file
    historyFile.append(appendLine);
  }


  /**
   * Gets the number of lines of past commands that need to be printed and
   * prints them
   * 
   * @param lastLines The number of past commands that the user wants to see
   */
  public static void printHistory(int lastLines) {
    // separates the file contents by line into an array
    String lines[] = Interpreter.inputToArray(historyFile.getFileContents(),
        "\n");

    int startLineIndex = numLines - lastLines;
    // loop through each line starting at startLineIndex and print them
    for (int i = startLineIndex; i < numLines; i++) {
      System.out.println(lines[i]);
    }
  }

  /**
   * Gets the all past commands and prints them
   */
  public static void printAllHistory() {
    printHistory(numLines);
  }
}
