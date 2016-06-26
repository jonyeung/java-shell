package driver;

public class List {

  /**
   * ?????????????????????????????????????????????????
   */
  public static void ls(FileSystem fileSystem, String[] filepaths)
      throws CommandException {

    // if no file paths given then print contents of current directory
    if (filepaths == null) {
      listContents(fileSystem, fileSystem.getCurrentDirectory());

    } else {
      // loop through each file path given
      for (String path : filepaths) {
        // split the path into a list and get the new directory name
        String[] pathway = Interpreter.filepathToArray(path);
        String fileName = pathway[pathway.length - 1];

        // Get the parent directory and get the file that we want to ls
        Directory parent = fileSystem.getParentDirectory(path);
        File currFile = fileSystem.searchFile(parent, fileName);
        System.out.println(path + ":");
        
        // if it is a directory then print the contents of the file otherwise,
        // it is a file and it will read it.
        if (currFile instanceof Directory) {
          listContents(fileSystem, (Directory) currFile);

        } else {
          // TODO call cat command

        }
        System.out.println("");
      }
    }
  }

  private static void listContents(FileSystem fileSystem, Directory dir)
      throws CommandException {

    // Print all the elements in the storedFiles found in dir
    for (File file : dir.getStoredFiles()) {
      System.out.println(file.getName());
    }
  }


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
  public static void listContentsAbsolute(FileSystem fileSystem, String path)
      throws CommandException {

    // reach the file found at the pathway
    Directory dir = fileSystem.traversePath(path);

    // Print all the elements in the storedFiles found in dir
    for (File file : dir.getStoredFiles()) {
      System.out.println(file.getName());
    }
  }
}
