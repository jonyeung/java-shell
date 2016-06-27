package driver;

/**
 * This class allows you to print the contents of a file
 */
public class Cat {

  /**
   * Returns the contents of each file given
   * 
   * @param fileSystem The file system with all the files and directories
   * @param filepaths The files that we want to read
   * @throws CommandException
   */
  public static String cat(FileSystem fileSystem, String[] filepaths)
      throws CommandException {

    int count = 1;
    String output = "";

    // Loop through each file path given and read its contents
    for (String path : filepaths) {

      // Get the file at path
      File currFile = fileSystem.getFile(path);

      // Print the contents of currFile
      if (currFile instanceof TextFile) {

        if (count != 1) {
          output += "\n\n\n\n";
        }

        output += ((TextFile) currFile).getFileContents();
      } else {
        throw new CommandException(path + " is not a file.");
      }
      count++;
    }
    return output;
  }
}
