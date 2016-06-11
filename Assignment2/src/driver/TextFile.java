
public class TextFile extends File{

  String fileName;
  String fileContents;
  
  public TextFile(String name, String text) {
    super(name);
    fileContents = text;
  }
  
  /**
   * Write to the text file, overwriting whatever text was already stored.
   * @param newText The new text contents the file is to hold.
   */
  public void write(String newText) {
    fileContents = newText;
  }
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
