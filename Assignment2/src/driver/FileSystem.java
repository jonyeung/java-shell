package driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import driver.File;

/**
 * Represents a FileSystem of object File. Starts as a single root
 */
public class FileSystem {

  private Directory rootDirectory = new Directory("root");
  private Directory currentDirectory = rootDirectory;

  /**
   * Default FileSystem constructor
   */
  public FileSystem() {

    super();
  }

  /**
   * Get the root directory
   */
  public Directory getRootDirectory() {

    return rootDirectory;
  }

  /**
   * Get the current working directory
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
   * Returns a file named dirName by searching the directory curr
   * 
   * @param curr The directory to look for the file
   * @param dirName The name of the file we want
   * @return File The File that we are looking for
   * @throws CommandException If search file does not exist
   */
  private File searchFile(Directory curr, String dirName)
      throws CommandException {

    // Get all the files in the curr directory
    ArrayList<File> storedFiles = curr.getStoredFiles();
    int i = 0;
    boolean notFound = true;
    File returnFile = null;

    // Loop through each file and check if it has the same name as dirName
    while (i < storedFiles.size() && notFound) {

      File file = storedFiles.get(i);
      // If the directory is found, then search the next thing on pathway
      if (file.getName().equals(dirName)) {
        notFound = false;
        returnFile = file;
      }
      i++;
    }

    if (notFound) {
      throw new CommandException("File " + dirName + " does not exist.");
    }
    return returnFile;
  }

  /**
   * Returns a directory using the pathway and starts the search in directory
   * curr
   * 
   * @param curr The starting directory to look for file in pathway
   * @param pathway The array of file/directory names to find a file
   * @return Directory The Directory that we are looking for
   * @throws CommandException If search file does not exist, If trying to search
   *         the parent of the root
   */
  private Directory getDirectory(Directory curr, String[] pathway)
      throws CommandException {

    Directory returnDirectory = curr;

    // If no pathway is given, then return curr
    if (pathway.length != 0) {

      String searchDir = pathway[0];
      String newPathway[] = Arrays.copyOfRange(pathway, 1, pathway.length);

      // Check if the search directory is '..', then search if parent exists
      if (searchDir.equals("..") && !curr.equals(rootDirectory)) {
        returnDirectory = this.getDirectory(curr.getParent(), newPathway);

      } else if (searchDir.equals("..")) {
        throw new CommandException("You are currently at the root directory.");

      } else {

        File file;
        // Check if the search directory is '.', then it is the current
        if (searchDir.equals(".")) {
          file = curr;
        } else {
          // Find the search directory in the current directory.
          file = searchFile(curr, searchDir);
        }
        // Check that the file found is a directory and that we can look into it
        if (file instanceof Directory) {
          returnDirectory = this.getDirectory((Directory) file, newPathway);
        }
      }
    }
    return returnDirectory;
  }

  /**
   * Returns a file using the file path given
   * 
   * @param path The file path in the format given by the user
   * @return Directory The Directory that we are looking for
   * @throws CommandException If search file does not exist, If trying to search
   *         the parent of the root, Bad format of filepath
   */
  public Directory traversePath(String path) throws CommandException {

    Directory returnFile;

    if (path.length() == 0) {
      returnFile = this.getCurrentDirectory();
    } else {
      // Parse the path by converting it to a list
      String[] pathway = Interpreter.filepathToArray(path);

      // Check if path is absolute or relative
      boolean relative = true;

      if (path.charAt(0) == '/') {
        relative = false;
      }

      // Search the correct directory for the file in pathway
      if (relative) {
        returnFile = this.getDirectory(currentDirectory, pathway);
      } else {
        returnFile = this.getDirectory(rootDirectory, pathway);
      }
    }

    return returnFile;
  }

  /**
   * Returns the parent directory of the file path given
   * 
   * @param path The file path in the format given by the user
   * @return Directory The parent directory
   * @throws CommandException If search file does not exist, If trying to search
   *         the parent of the root, Bad format of filepath
   */
  public Directory getParentDirectory(String path) throws CommandException {

    // Split the path into a list and get the new directory name
    String[] pathway = Interpreter.filepathToArray(path);
    String newFile = pathway[pathway.length - 1];

    // Get the parent's file path
    int lastIndex = path.length() - newFile.length();
    if (path.charAt(path.length() - 1) == '/') {
      lastIndex--;
    }
    String parentPath = path.substring(0, lastIndex);
    // Change into the parent's directory and store the new directory in it
    Directory parent = this.traversePath(parentPath);

    return parent;
  }

  /**
   * Returns the file using the file path
   * 
   * @param path The file path in the format given by the user
   * @return File The file wanted
   * @throws CommandException If search file does not exist, If trying to search
   *         the parent of the root, Bad format of filepath
   */
  public File getFile(String path) throws CommandException {

    // Split the path into a list and get the new directory name
    String[] pathway = Interpreter.filepathToArray(path);
    if (pathway.length == 0) {
      throw new CommandException("Cannot get file at " + path);
    }
    String fileName = pathway[pathway.length - 1];

    // Get the parent directory and get the file that we want to ls
    Directory parent = this.getParentDirectory(path);
    File currFile;

    if (fileName.equals(".")) {
      currFile = parent;
    } else if (fileName.equals("..")) {
      currFile = parent.getParent();
      // If trying to get the parent of the root, throw an exception
      if (currFile == null) {
        throw new CommandException("Root does not have a parent directory");
      }
    } else {
      currFile = this.searchFile(parent, fileName);
    }

    return currFile;
  }

  /**
   * Recursively go through each directory and add each directory to the hash
   * table fm
   * 
   * @param curr The current directory to look through
   * @param path The current file path of directory curr
   * @param fm A hash table containing file path that map to corresponding files
   */
  public void recurseDirectories(Directory curr, String path,
      Hashtable<String, File> fm) {

    // Get all the files in the curr directory
    ArrayList<File> storedFiles = curr.getStoredFiles();

    // Add the file path and current directory to the hash table
    fm.put(path, curr);

    // Go through each file in the current directory. If it is a directory
    // recursively go through that directory and add its directories to the hash
    // table
    for (int i = 0; i < storedFiles.size(); i++) {
      File currFile = storedFiles.get(i);

      if (currFile instanceof Directory) {
        String newPath = path + "/" + currFile.getName();
        this.recurseDirectories((Directory) currFile, newPath, fm);
      }
    }
  }

}
