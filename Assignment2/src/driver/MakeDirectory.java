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

    // split the path into a list and get the new directory name
    String[] pathway = Interpreter.filepathToArray(path);
    String newDir = pathway[pathway.length - 1];

    // Get the parent directory
    Directory parent = fileSystem.getParentDirectory(path);

    // check if the directory name is valid
    if (!checkDirName(newDir, parent)) {
      throw new CommandException(
          "\"" + path + "\"" + " is not a valid directory name.");
    }
    parent.storeFile(new Directory(newDir));
  }

  /**
   * Checks if the directory name is valid. It is valid if no special characters
   * are used in the name and if a file with the same name in the parent
   * directory does not exist
   * 
   * @param dirName The directory name that this are checking
   * @param parentDir The directory that the new directory will be made in
   * @return boolean Whether the directory name is valid
   */
  private static boolean checkDirName(String dirName, Directory parentDir) {

    // check that no current file in the parent directory has the same name
    boolean result = !parentDir.fileInDirectory(dirName);

    // check that the dirName doesn't contain special characters
    int i = 0;
    while (i < dirName.length() && result) {
      if (!Character.isLetterOrDigit(dirName.charAt(i))) {
        result = false;
      }
      i++;
    }

    return result;
  }
}
