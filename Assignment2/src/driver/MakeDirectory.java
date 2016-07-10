package driver;

/**
 * This class consists exclusively of static methods for Make Directory (mkdir)
 */
public class MakeDirectory {

  /**
   * Create a directory given a file path
   * 
   * @param fileSystem The file system with all the files and directories
   * @param path The file path of the new directory
   * @throws CommandException If the new directory name uses special characters
   *         or the name is already used in another file in the same directory
   */
  public static void makeADirectory(FileSystem fileSystem, String path)
      throws CommandException {

    // Split the path into a list and get the new directory name
    String[] pathway = Interpreter.filepathToArray(path);
    String newDir = pathway[pathway.length - 1];

    // Get the parent directory
    Directory parent = fileSystem.getParentDirectory(path);

    // Check if the directory name is valid
    if (!Interpreter.checkFileName(newDir, parent)) {
      throw new CommandException(path + " is not a valid directory name.");

      // Check that no current file in the parent directory has the same name
    } else if (parent.fileInDirectory(newDir)) {
      throw new CommandException("Directory " + path + " already exists.");
    }
    parent.storeFile(new Directory(newDir, parent));
  }
}
