package driver;

public class Move {


  /**
   * Moves the item found at oldPath to the directory found at newPath
   * 
   * @param fileSys The file system being worked with
   * @param oldPath The location of the file to be moved
   * @param newPath The destination of the file that is being moved
   * @param deleteOriginal True if the item is being moved and not copied
   * @throws CommandException if the newPath does not specify a directory
   */
  public static void moveItem(FileSystem fileSys, String oldPath, 
      String newPath, boolean deleteOriginal) throws CommandException {
    // get the item specified at oldPath
    File item = fileSys.getFile(oldPath);

    if (deleteOriginal) {
      // remove this item from its parent directory
      item.getParent().getStoredFiles().remove(item);
    } else {
      // make a copy of the item
      if (item instanceof Directory) {
        item = Move.copyDirectory((Directory)item);
      } else {
        item = Move.copyTextFile((TextFile)item);
      }
    }
      
    // get the directory specified at newPath
    try {
      Directory newLocation = (Directory) fileSys.getFile(newPath);
      // store the item in this directory
      newLocation.storeFile(item);
    } catch (ClassCastException e) {
      throw new CommandException("newPath does not specify a directory");
    }

  }

  /**
   * Makes a copy of the given directory
   * 
   * @param Directory The directory being copied
   * @return Directory A copy of the directory
   */
  private static Directory copyDirectory(Directory file) {
    // recreate the directory with the same name and parent file
    Directory copy = new Directory(file.getName(), file.getParent());
    // recreate all stored files
    for (File item : ((Directory) file).getStoredFiles()) {
      if (item instanceof Directory) {
        copy.storeFile(Move.copyDirectory((Directory)item));
      } else {
        copy.storeFile(Move.copyTextFile((TextFile)item));
      }
    }
    return copy;
  }
  
  /**
   * Makes a copy of the given text file
   * 
   * @param TextFile The text file being copied
   * @return TextFile A copy of the text file
   */
  private static TextFile copyTextFile(TextFile file) {
    // recreate the text file with the same name, contents, and parent
    TextFile copy = new TextFile(file.getName(), 
          ((TextFile) file).getFileContents(), file.getParent());
    return copy;
  }
}
