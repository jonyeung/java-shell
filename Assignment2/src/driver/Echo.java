package driver;

/**
 * This class implements the echo command as per assignment requirements by
 * utilizing the filesystem and path names to correctly append or overwrite text
 * in a textfile
 */
public class Echo {

  /**
   * Writes the string to an out file if given, otherwise return the string
   * 
   * @param fileSys The file system being utilized
   * @param commandArgs The arguments given by the user
   * @return String Returns the string given if no out file is given
   * @throws CommandException
   */
  public static String executeEcho(FileSystem fileSys, String[] commandArgs)
      throws CommandException {

    String output = "";

    // If all 3 parameters for echo exist
    if (commandArgs.length == 3) {
      if (commandArgs[1].equals(">")) {
        echoNew(fileSys, commandArgs[0], commandArgs[2], false);
      } else if (commandArgs[1].equals(">>")) {
        echoNew(fileSys, commandArgs[0], commandArgs[2], true);
      } else {
        throw new CommandException("Invalid arguments given.");
      }
      // If only 1 parameter exists, print onto command line
    } else if (commandArgs.length == 1) {
      output = commandArgs[0];
    } else {
      throw new CommandException("Please read the manual for Echo.");
    }

    return output;
  }

  /**
   * Creates a new text file who's contents contain only string. If the text
   * file already exists, it overwrites all its contents with the new string.
   * 
   * @param fileSys The file system being utilized
   * @param fileContents The contents of the text file
   * @param path The location of the text file
   * @param chevrons Boolean that is true if there are 2 chevrons, false o/w
   * @throws CommandException
   */
  public static void echoNew(FileSystem fileSys, String fileContents,
      String path, Boolean chevrons) throws CommandException {

    // Get the name of the text file
    String[] pathway = Interpreter.filepathToArray(path);
    String fileName = pathway[pathway.length - 1];

    Directory curr = fileSys.getParentDirectory(path);

    // If the file is already in the directory, then get it
    if (curr.fileInDirectory(fileName)) {

      File file = fileSys.getFile(path);

      if (file instanceof TextFile) {
        // If double chevrons are given, then append to the file, otherwise
        // overwrite the old file
        if (chevrons) {
          ((TextFile) file).append(fileContents);
        } else {
          ((TextFile) file).write(fileContents);
        }
      }

    } else {
      if (chevrons) {
        throw new CommandException("Cannot append to non-existing file.");
      } else {
        // Check if the file name is valid
        if (!Interpreter.checkFileName(fileName, curr)) {
          throw new CommandException(path + " is not a valid file name.");
        }
        // Add a text file to this directory with contents string
        TextFile newFile = new TextFile(fileName, fileContents, curr);
        curr.storeFile(newFile);
      }
    }
  }
}
