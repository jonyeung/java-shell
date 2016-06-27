package driver;

import java.util.ArrayList;

/**
 * This class implements the echo command as per assignment requirements by
 * utilizing the filesystem and path names to correctly append or overwrite text
 * in a textfile
 */
public class Echo {

  /**
   * Creates a new text file who's contents contain only string. If the text
   * file already exists, it overwrites all its contents with the new string.
   * 
   * @param fileSys The file system being utilized
   * @param fileContents The contents of the text file
   * @param path The location of the text file
   * @param chevrons Boolean that is true if there're 2 chevrons, false o/w
   * @throws CommandException
   */
  public static void echoNew(FileSystem fileSys, String fileContents,
      String path, Boolean chevrons) throws CommandException {

    // Get the name of the text file
    String[] pathway = Interpreter.filepathToArray(path);
    String fileName = pathway[pathway.length - 1];

    // Determine if the path is relative or not
    boolean relative = path.contains("/");

    Directory curr;

    if (relative) {
      // Check if the file exists in the current directory
      curr = fileSys.getCurrentDirectory();

    } else {
      // Reach the parent directory of the text file
      curr = fileSys.traversePath(path);
    }

    if (curr.fileInDirectory(fileName)) {
      // Get the existing text file with the same name
      ArrayList<File> storedFiles = curr.getStoredFiles();

      for (File file : storedFiles) {

        if (file.getName().equals(fileName)) {
          if (chevrons) {
            ((TextFile) file).append(fileContents);
          } else {
            ((TextFile) file).write(fileContents);
          }
        }
      }
    } else {
      // Add a text file to this directory with contents string
      TextFile newFile = new TextFile(fileName, fileContents, curr);
      curr.storeFile(newFile);
    }
  }
}
