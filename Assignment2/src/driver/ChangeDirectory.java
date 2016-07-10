package driver;

/**
 * This class consists exclusively of static methods for Change Directory (cd)
 */
public class ChangeDirectory {

  /**
   * Change the user's current directory
   * 
   * @param fileSystem The file system with all the files and directories
   * @param path The new directory the user changes to
   * @throws CommandException If trying to cd to a file that does not exist, If
   *         trying to cd to the parent of the root, if path is not to a
   *         directory
   */
  public static void changeCurrentDirectory(FileSystem fileSystem, String path)
      throws CommandException {

    // Get the file from the path
    Directory destinationDirectory = fileSystem.traversePath(path);

    // Set the current directory of fileSystem to this file
    fileSystem.setCurrentDirectory(destinationDirectory);
  }
}
