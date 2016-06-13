package driver;

public class TextFile extends File {

  String fileName;
  String fileContents;

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
}
