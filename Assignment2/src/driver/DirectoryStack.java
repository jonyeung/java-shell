package driver;

import java.util.Stack;

public class DirectoryStack {

  private static Stack<String> savedDirectories = new Stack<String>();

  /**
   * Puts the current working directory onto the stack of directories and
   * changes the current working directory to the one specified
   * 
   * @param fileSystem The file system with all the files and directories
   * @param filepath The current directory will be changed to this
   * @throws CommandException 
   */
  public static void pushd(FileSystem fileSystem, String filepath) throws CommandException {

    // use pwd to get the name of the current directory
    String currFilePath = PrintWorkingDirectory.getFilepath(fileSystem);
    // push the current directory onto the stack
    savedDirectories.add(currFilePath);
    // use cd to change into changeDirectory
    ChangeDirectory.changeCurrentDirectory(fileSystem, filepath);
  }

  /**
   * Gets the first directory saved onto the stack and changed the current
   * working directory to it
   * 
   * @param fileSystem The file system with all the files and directories
   * @throws CommandException 
   */
  public static void popd(FileSystem fileSystem) throws CommandException {

    // pops the first element and use cd
    if (savedDirectories.isEmpty()) {
      // Raise exception when there are no directories to pop
      try {
        throw new CommandException(
            "No directories can be removed from the directory stack.");
      } catch (CommandException e) {
        // Print the message
        System.out.println(e.getMessage());
      }
    } else {
      String filepath = savedDirectories.pop();
      ChangeDirectory.changeCurrentDirectory(fileSystem, filepath);
    }
  }
}
