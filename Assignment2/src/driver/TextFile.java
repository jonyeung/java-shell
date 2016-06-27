package driver;

/**
 * This class represents a text file that holds text which can be manipulated by
 * the user with echo or cat
 */
public class TextFile extends File {

  private String fileContents;

  /**
   * TextFile constructor with no parent and no contents.
   * 
   * @param name The name of the TextFile.
   */
  public TextFile(String name) {

    super(name);
  }

  /**
   * TextFile constructor with no parent and non-empty contents.
   * 
   * @param name The name of the TextFile.
   * @param text The content of the TextFile.
   */
  public TextFile(String name, String text) {

    super(name);
    fileContents = text;
  }

  /**
   * TextFile constructor with a parent and no contents.
   * 
   * @param name The name of the TextFile.
   * @param parentFile The parent of the TextFile.
   */
  public TextFile(String name, Directory parentFile) {

    super(name, parentFile);
  }

  /**
   * TextFile constructor with a parent and non-empty contents.
   * 
   * @param name The name of the TextFile.
   * @param text The content of the TextFile.
   * @param parentFile The parent of the TextFile.
   */
  public TextFile(String name, String text, Directory parentFile) {

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
    // fileContents becomes newLine if it was not already holding anything
    if (fileContents == null) {
      fileContents = newLine;
    } else {
      fileContents += "\n" + newLine;
    }
  }

  /**
   * Getter for fileContents
   * 
   * @return String The contents held in the text file.
   */
  public String getFileContents() {

    return fileContents;
  }
}
