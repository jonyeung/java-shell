package driver;

/**
 * This class consists exclusively of static methods for Make Directory (mkdir)
 */
public class MakeDirectory {

  /**
   * Create a directory given a relative file path
   * 
   * @param fileSystem The file system with all the files and directories
   * @param dirName The name of the new directory being made
   * @throws CommandException
   */
  public static void mkdirRelative(FileSystem fileSystem, String dirName)
      throws CommandException {

    // If dirName isn't in the current directory, then create it
    if (fileSystem.getCurrentDirectory().fileInDirectory(dirName) == false) {
      // create a new directory in the current directory of the file system
      fileSystem.getCurrentDirectory().storeFile(new Directory(dirName));
    } else {
      // Throw exception if it's already in the current directory
      throw new CommandException(
          dirName + " is already an existing directory.");
    }
  }

  /**
   * Create a directory given an absolute file path
   * 
   * @param fileSystem The file system with all the files and directories
   * @param path The absolute file path of the new directory
   */
  public static void mkdirAbsolute(FileSystem fileSystem, String path) {

    // split the path into a list
    String[] pathway = Interpreter.filepathToArray(path);

    // get the name of the new directory being made
    String newDir = pathway[pathway.length - 1];

    // get the parent directory of the new directory
    String parentPath = path.substring(0, path.lastIndexOf("/"));
    Directory parent = (Directory) fileSystem.traversePath(parentPath);

    // create a new directory in the parent directory
    parent.storeFile(new Directory(newDir));
  }
}
