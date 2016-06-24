package driver;

/**
 * This class consists exclusively of static methods for Change Directory
 * (cd)
 */
public class ChangeDirectory {

  /**
   * Change the user's current directory
   * 
   * @param fileSystem The file system with all the files and directories
   * @param path The new directory the user changes to
   */
  public static void changeCurrentDirectory(FileSystem fileSystem,
      String path) {

    // get the file from the path
    Directory destinationDirectory = fileSystem.traversePath(path);
 
    // set the current directory of fileSystem to this file
    fileSystem.setCurrentDirectory(destinationDirectory);
  }
}
