package driver;

import java.util.ArrayList;

/**
 * The Directory Class. This class consists of 2 constructors, the first taking
 * in only a name <type str> parameter and the second taking in a name <type
 * str> and a parentFile <type Directory> as parameters. This class also
 * consists of methods to store the file and get stored files.
 * 
 */
public class Directory extends File {

  private ArrayList<File> storedFiles;

  /**
   * Directory constructor with no parent.
   * 
   * @param name The name of the directory.
   */
  public Directory(String name) {

    super(name);
    storedFiles = new ArrayList<File>();
  }

  /**
   * Directory constructor with a parent file.
   * 
   * @param name The name of the directory.
   * @param parentFile The parent of this directory.
   */
  public Directory(String name, Directory parentFile) {

    super(name, parentFile);
  }

  /**
   * Adds a file to the current directory.
   * 
   * @param file The file to be stored in the directory.
   */
  public void storeFile(File file) {

    // Add the file to the set of stored files
    storedFiles.add(file);

    // Set the file's parent to be the directory it is being stored in
    file.setParent(this);
  }

  /**
   * Adds a directory to the current directory.
   * 
   * @param dir The directory to be stored in the directory.
   */
  public void storeFile(Directory dir) {

    // Add the file to the set of stored files
    storedFiles.add(dir);

    // Set the file's parent to be the directory it is being stored in
    dir.setParent(this);
  }

  /**
   * Getter for storedFiles.
   * 
   * @return Set<File> The set of all the stored files in the directory.
   */
  public ArrayList<File> getStoredFiles() {

    return storedFiles;
  }


  /**
   * @param directoryName The name of the directory to check in current
   *        directory.
   * @return Boolean The boolean that determines whether or not directoryName is
   *         in current directory.
   */
  public boolean fileInDirectory(String directoryName) {
    // Initialize boolean to keep track of whether or not directoryName is in
    // current directory
    Boolean fileResult = false;

    // Loop through each directory name in the current directory and check
    // if they have the same name as directoryName
    for (int i = 0; i < storedFiles.size(); i++) {
      // If any of them do, then fileResult becomes true
      if (storedFiles.get(i).getName().equals(directoryName)) {
        fileResult = true;
      }
    }
    return fileResult;
  }
}
