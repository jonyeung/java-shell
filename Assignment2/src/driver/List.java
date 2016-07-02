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

    String outputDirectories = "";
    String outputFiles = "";
    String output;

    // If no file paths given then return contents of current directory
    if (filepaths == null) {
      output = listContents(fileSystem, fileSystem.getCurrentDirectory());

    } else {
      
      // Loop through each file path given
      for (String path : filepaths) {

        // Get the file at path
        File currFile = fileSystem.getFile(path);

        // If it is a directory then return the contents of the file, otherwise
        // it is a file and it will read it.
        if (currFile instanceof Directory) {
          outputDirectories += "\n\n" + path + ":\n"
              + listContents(fileSystem, (Directory) currFile);

        } else {
          // Add the file name
          outputFiles = path + " " + outputFiles;
        }
      }
      // combine outputFiles and outputDirectories
      if (outputFiles.equals("")) {
        output = outputDirectories.trim();
      } else {
        output = outputFiles.trim() + outputDirectories;
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

    // Return all the elements in the storedFiles found in dir
    for (File file : dir.getStoredFiles()) {
      output += file.getName() + "\n";
    }

    if (!output.equals("")) {
      output = output.substring(0, output.length() - 1);
    }
    return output;
  }
}
