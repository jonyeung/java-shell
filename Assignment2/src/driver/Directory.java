package driver;

import java.util.Set;


public class Directory extends File {

  private Set<File> storedFiles;

  /**
   * Directory constructor with no parent
   * 
   * @param name The name of the directory.
   */
  public Directory(String name) {
    super(name);
  }

  /**
   * Directory constructor with a parent file
   * 
   * @param name The name of the directory.
   * @param parentFile The parent of this directory.
   */
  public Directory(String name, File parentFile) {
    super(name, parentFile);
  }

  /**
   * Adds a file to the current directory.
   * 
   * @param file The file to be stored in the directory.
   */
  public void storeFile(File file) {
    // add the file to the set of stored files
    storedFiles.add(file);
  }

  /**
   * Getter for storedFiles.
   * 
   * @return Set<File> The set of all the stored files in the directory.
   */
  public Set<File> getStoredFiles() {
    return storedFiles;
  }

}
