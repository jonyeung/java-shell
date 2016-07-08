package driver;

import java.util.Stack;

/**
 * This class allows you to save directories onto a stack and remove from it,
 * changing the current working directory to the most recently saved directory
 */
public class DirectoryStack {

  private static Stack<String> savedDirectories = new Stack<String>();

  /**
   * Returns the number of directories that are saved in the stack
   * 
   * @return int The number of directories saved
   */
  public static int numDirectories() {

    return savedDirectories.size();
  }

  /**
   * Puts the current working directory onto the stack of directories and
   * changes the current working directory to the one specified
   * 
   * @param fileSystem The file system with all the files and directories
   * @param filepath The current directory will be changed to this
   * @throws CommandException If trying to cd to a file that does not exist, If
   *         trying to cd to the parent of the root, Bad format of filepath
   */
  public static void pushd(FileSystem fileSystem, String filepath)
      throws CommandException {

    // Use pwd to get the name of the current directory
    String currFilePath = PrintWorkingDirectory.printWD(fileSystem);

    // Push the current directory onto the stack
    savedDirectories.add(currFilePath);

    // Use cd to change into changeDirectory
    ChangeDirectory.changeCurrentDirectory(fileSystem, filepath);
  }

  /**
   * Gets the first directory saved onto the stack and changed the current
   * working directory to it
   * 
   * @param fileSystem The file system with all the files and directories
   * @throws CommandException If trying to cd to a file that does not exist, If
   *         trying to cd to the parent of the root, Bad format of filepath,
   *         Trying to pop from empty directory stack
   */
  public static void popd(FileSystem fileSystem) throws CommandException {

    // Pop the first element and use cd
    if (savedDirectories.isEmpty()) {

      // Throw exception when there are no directories to pop
      throw new CommandException(
          "No directories can be removed from the directory stack.");
    } else {

      String filepath = savedDirectories.pop();
      ChangeDirectory.changeCurrentDirectory(fileSystem, filepath);
    }
  }

  /**
   * Removes all directories saved.
   */
  public static void removeSavedDirectories() {

    savedDirectories.clear();
  }
}
