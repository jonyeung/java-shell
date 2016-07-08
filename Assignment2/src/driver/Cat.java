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
   */
  public static String cat(FileSystem fileSystem, String[] filepaths) {

    String output = "";

    // Loop through each file path given and read its contents
    for (String path : filepaths) {

      try {
        // Get the file at path
        File currFile = fileSystem.getFile(path);
        // Print the contents of currFile
        if (currFile instanceof TextFile) {
          output +=
              path + ":\n" + ((TextFile) currFile).getFileContents() + "\n\n";
        } else {
          throw new CommandException("");
        }
      } catch (CommandException e) {
        output += path + " is not a file.\n\n";
      }

    }
    return output.trim();
  }
}
