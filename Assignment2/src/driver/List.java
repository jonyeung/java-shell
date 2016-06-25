package driver;

public class List {

  /**
   * Prints the contents of the current directory.
   * 
   * @param fileSystem The file system containing all the files and directories
   */
  public static void listContents(FileSystem fileSystem) {

    // get the current directory the user is in
    Directory dir = fileSystem.getCurrentDirectory();

    // Print all the elements in the storedFiles found in dir
    for (File file : dir.getStoredFiles()) {
      System.out.println(file.getName());
    }
  }

  /**
   * Prints the contents of the directory found at the given pathway.
   * 
   * @param fileSystem The file system containing all the files and directories
   * @param path The pathway specifying a certain file
   * @throws CommandException
   */
  public static void listContentsAbsolute(FileSystem fileSystem,
      String path) throws CommandException {

    // reach the file found at the pathway
    Directory dir = fileSystem.traversePath(path);

    // Print all the elements in the storedFiles found in dir
    for (File file : dir.getStoredFiles()) {
      System.out.println(file.getName());
    }
  }
}
