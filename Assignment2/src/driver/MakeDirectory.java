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
    }
    parent.storeFile(new Directory(newDir));
  }
}
