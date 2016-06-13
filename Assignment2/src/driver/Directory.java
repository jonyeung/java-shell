package driver;
import java.util.Set;

public class Directory extends File {

  String directoryName;
  Set<File> storedFiles;

  // constructor with no parent
  public Directory(String name) {
    super(name);
  }

  // constructor with a parent file
  public Directory(String name, File parentFile) {
    super(name, parentFile);
  }

  /**
   * Adds a file to the current directory. 
   * @param file The file to be stored in the directory.
   */
  public void storeFile(File file) {
    // add the file to the set of stored files
    storedFiles.add(file);
  }
}
