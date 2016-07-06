package driver;

public class Move {


  /**
   * Moves the item found at oldPath to the directory found at newPath
   * 
   * @param fileSys The file system being worked with
   * @param oldPath The location of the file to be moved
   * @param newPath The destination of the file that is being moved
   * @param deleteOriginal True if the item is being moved and not copied
   * @throws CommandException
   */
  public static void moveItem(FileSystem fileSys, String oldPath, 
      String newPath, boolean deleteOriginal) throws CommandException {
    // get the item specified at oldPath
    File item = fileSys.getFile(oldPath);

    if (deleteOriginal) {
      // remove this item from its parent directory
      item.getParent().getStoredFiles().remove(item);
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

}