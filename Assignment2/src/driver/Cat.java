package driver;

/**
 * This class allows you to print the contents of a file
 */
public class Cat {

  /**
   * Puts the current working directory onto the stack of directories and
   * changes the current working directory to the one specified
   * 
   * @param fileSystem The file system with all the files and directories
   * @param filepaths The files that we want to read
   * @throws CommandException
   */
  public static String cat(FileSystem fileSystem, String[] filepaths)
      throws CommandException {

    int count = 1;
    String output = "";
    
    // loop through each file path given and read its contents
    for (String path : filepaths) {
      // get the file at path
      File currFile = fileSystem.getFile(path);
      
      // print the contents of currFile
      if (currFile instanceof TextFile) {
        if (count == 1) {
          output += "\n\n";
        }
        output += ((TextFile) currFile).getFileContents();
      }
      count++;
    }
    return output;
  }


}
