package driver;

import java.util.ArrayList;
import java.util.Arrays;
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
   */
  public File searchFile(Directory curr, String dirName) {

    // get all the files in the curr directory
    ArrayList<File> storedFiles = curr.getStoredFiles();
    int i = 0;
    boolean notFound = true;
    File returnFile = null;

    // loop through each file and check if it has the same name as dirName
    while (i < storedFiles.size() && notFound) {

      File file = storedFiles.get(i);
      // if the directory is found, then search the next thing on pathway
      if (file.getName().equals(dirName)) {
        notFound = false;
        returnFile = file;
      }
      i++;
    }

    if (notFound) {
      try {
        throw new CommandException("Invalid directory name.");
      } catch (CommandException e) {
        // Print the message
        System.out.println(e.getMessage());
      }
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
   * @throws CommandException
   */
  private Directory getDirectory(Directory curr, String[] pathway)
      throws CommandException {

    Directory returnDirectory = curr;
    // if no pathway is given, then return curr
    if (pathway.length != 0) {

      String searchDir = pathway[0];
      String newPathway[] = Arrays.copyOfRange(pathway, 1, pathway.length);

      // check if the search directory is '..', then search if parent exists
      if (searchDir.equals("..") && !curr.equals(rootDirectory)) {
        returnDirectory = this.getDirectory(curr.getParent(), newPathway);

      } else if (searchDir.equals("..")) {
        throw new CommandException("You are currently at the root directory.");

      } else {

        File file;
        // check if the search directory is '.', then it is the current
        if (searchDir.equals(".")) {
          file = curr;
        } else {
          // find the search directory in the current directory.
          file = searchFile(curr, searchDir);
        }
        // check that the file found is a directory and that we can look into it
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
   * @throws CommandException
   */
  public Directory traversePath(String path) throws CommandException {

    Directory returnFile;

    if (path.length() == 0) {
      returnFile = this.getCurrentDirectory();
    } else {
      // parse the path by converting it to a list
      String[] pathway = Interpreter.filepathToArray(path);

      // check if path is absolute or relative
      boolean relative = true;

      if (path.charAt(0) == '/') {
        relative = false;
      }

      // searches the correct directory for the file in pathway
      if (relative) {
        returnFile = this.getDirectory(currentDirectory, pathway);
      } else {
        returnFile = this.getDirectory(rootDirectory, pathway);
      }
    }
    
    return returnFile;
  }
}
