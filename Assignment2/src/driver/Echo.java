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
   * @param fileSys The file system being utilized @ string The text file
   *        contents @ path The location of the text file @ chevrons Whether or
   *        not there are 2 chevrons
   * 
   * @throws CommandException
   */
  public static void echoNew(FileSystem fileSys, String string, String path,
      Boolean chevrons) throws CommandException {
    // get the name of the text file
    String[] pathway = Interpreter.filepathToArray(path);
    String fileName = pathway[pathway.length - 1];

    // determine if the path is relative or not
    boolean relative = path.contains("/");

    Directory curr;

    if (relative) {
      // check if the file exists in the current directory
      curr = fileSys.getCurrentDirectory();

    } else {
      // reach the parent directory of the text file
      curr = fileSys.traversePath(path);
    }

    if (curr.fileInDirectory(fileName)) {
      // get the existing text file with the same name
      ArrayList<File> storedFiles = curr.getStoredFiles();
      for (File file : storedFiles) {
        if (file.getName().equals(fileName)) {
          // set this file's contents to string
          // try {
          // if (chevrons) {
          // ((TextFile) file).append(string);
          // } else {
          // ((TextFile) file).write(string);
          // }
          // } catch {
          // TODO: THROW EXCEPTION: NOT A TEXTFILE
          // }
        }
      }
    } else {
      // add a text file to this directory with contents string
      TextFile newFile = new TextFile(fileName, string, curr);
      curr.storeFile(newFile);
    }


  }
}
