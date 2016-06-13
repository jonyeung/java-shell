package driver;

public class TextFile extends File {

  private String fileContents;


  // basic constructor
  public TextFile(String name) {
    super(name);
  }

  // constructor with no parent
  public TextFile(String name, String text) {
    super(name);
    fileContents = text;
  }

  // constructor with a parent file
  public TextFile(String name, String text, File parentFile) {
    super(name, parentFile);
    fileContents = text;
  }

  /**
   * Write to the text file, overwriting whatever text was already stored.
   * 
   * @param newText The new text contents the file is to hold.
   */
  public void write(String newText) {
    fileContents = newText;
  }

  /**
   * Append a new line to the text file
   * 
   * @param newLine The new line that is to be appended to the file.
   */
  public void append(String newLine) {
    fileContents = fileContents + newLine;
  }
}
