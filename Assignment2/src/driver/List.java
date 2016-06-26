package driver;

public class List {

  /**
   * Prints the contents of each file in file paths.
   * 
   * @param fileSystem The file system containing all the files and directories
   * @param filepaths The file paths that we have to we want to list contents of
   * @throws CommandException
   */
  public static void list(FileSystem fileSystem, String[] filepaths)
      throws CommandException {

    // if no file paths given then print contents of current directory
    if (filepaths == null) {
      listContents(fileSystem, fileSystem.getCurrentDirectory());

    } else {

      int count = 1;

      // loop through each file path given
      for (String path : filepaths) {

        // get the file at path
        File currFile = fileSystem.getFile(path);

        if (count > 1) {
          System.out.println("");
        }
        System.out.println(path + ":");

        // if it is a directory then print the contents of the file otherwise,
        // it is a file and it will read it.
        if (currFile instanceof Directory) {
          listContents(fileSystem, (Directory) currFile);

        } else {
          // reads the file
          System.out.println(((TextFile) currFile).getFileContents());

        }
        count++;
      }
    }
  }

  /**
   * Prints the contents of the directory given
   * 
   * @param fileSystem The file system containing all the files and directories
   * @param dir The directory that we want to print its contents
   * @throws CommandException
   */
  private static void listContents(FileSystem fileSystem, Directory dir)
      throws CommandException {

    // Print all the elements in the storedFiles found in dir
    for (File file : dir.getStoredFiles()) {
      System.out.println(file.getName());
    }
  }

}
