package driver;

/**
 * This class returns the contents of a file
 */
public class List {

  /**
   * Returns the contents of each file in file paths.
   * 
   * @param fileSystem The file system containing all the files and directories
   * @param filepaths The file paths that we have to we want to list contents of
   * @return String The contents of each file path given
   * @throws CommandException
   */
  public static String list(FileSystem fileSystem, String[] filepaths)
      throws CommandException {

    String output = "";

    // if no file paths given then return contents of current directory
    if (filepaths == null) {
      output = listContents(fileSystem, fileSystem.getCurrentDirectory());

    } else {

      int count = 1;

      // loop through each file path given
      for (String path : filepaths) {

        // get the file at path
        File currFile = fileSystem.getFile(path);

        if (count > 1) {
          output += "\n";
        }
        output += path + ":\n";

        // if it is a directory then return the contents of the file, otherwise
        // it is a file and it will read it.
        if (currFile instanceof Directory) {
          output += listContents(fileSystem, (Directory) currFile);

        } else {
          // reads the file
          output += ((TextFile) currFile).getFileContents();
        }
        count++;
      }
    }
    return output;
  }

  /**
   * Returns the contents of the directory given
   * 
   * @param fileSystem The file system containing all the files and directories
   * @param dir The directory that we want to return its contents
   * @return String The contents of the directory
   * @throws CommandException
   */
  private static String listContents(FileSystem fileSystem, Directory dir)
      throws CommandException {

    String output = "";

    // Returns all the elements in the storedFiles found in dir
    for (File file : dir.getStoredFiles()) {
      output += file.getName() + "\n";
    }

    if (!output.equals("")) {
      output = output.substring(0, output.length() - 1);
    }
    return output;
  }

}
