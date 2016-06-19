package driver;

import java.util.ArrayList;
import java.util.Arrays;
import driver.File;

/**
 * Represents a Tree of object File. Starts as a single root
 */
public class FileSystem {

  /**
   * Default Tree constructor
   */
  public FileSystem() {

    super();
  }

  private Directory rootDirectory = new Directory("root");
  private Directory currentDirectory = rootDirectory;

  /**
   * Get the root directory
   * 
   */
  public Directory getRootDirectory() {
    return rootDirectory;
  }

  /**
   * Get the current working directory
   * 
   */
  public Directory getCurrentDirectory() {
    return currentDirectory;
  }

  /**
   * Set the current working directory
   * 
   * @param currentDirectory The directory that we want to change to
   */
  public void setCurrentDirectory(Directory currentDirectory) {
    this.currentDirectory = currentDirectory;
  }

  /**
   * Returns a file using the pathway and starts the search in directory curr
   * 
   * @param curr The starting directory to look for file in pathway
   * @param pathway The array of file/directory names to find a file
   * @return File The file that we are looking for
   */
  private File getFile(Directory curr, String[] pathway) {

    File returnFile = null;

    // if no pathway is given, then return curr
    if (pathway.length == 0) {

      returnFile = curr;
    } else {

      // loop through each file in curr and check if any match the name of
      // the first directory in pathway
      int i = 0;
      boolean notFound = true;

      ArrayList<File> storedFiles = curr.getStoredFiles();

      String searchDir = pathway[0];
      String newPathway[] = Arrays.copyOfRange(pathway, 1, pathway.length);

      // check if search directory is '..', then search if parent exists
      if (searchDir == ".." && !curr.equals(rootDirectory)) {

        returnFile = this.getFile(curr.getParent(), newPathway);
      } else {

        // TODO raise no parent exception
      }

      while (i < storedFiles.size() && notFound) {

        File file = storedFiles.get(i);

        // if the directory is found, then search the next thing on pathway
        if (file.getName().equals(searchDir) && file instanceof Directory) {
          notFound = false;
          returnFile = this.getFile((Directory) file, newPathway);
        }
      }

      if (notFound) {

        // TODO raise directory not found exception
      }
    }
    return returnFile;
  }

  /**
   * Returns a file using the file path given
   * 
   * @param path The file path in the format given by the user
   * @return File The file that we are looking for
   */
  public File traversePath(String path) {

    // parse the path by converting it to a list
    String[] pathway = Interpreter.filepathToArray(path);

    // check if path is absolute or relative
    boolean relative = true;

    if (path.charAt(0) == '/') {
      relative = false;
    }

    File returnFile;

    // searches the correct directory for the file in pathway
    if (relative) {
      returnFile = this.getFile(currentDirectory, pathway);
    } else {
      returnFile = this.getFile(rootDirectory, pathway);
    }
    return returnFile;
  }
}
