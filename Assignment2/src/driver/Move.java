package driver;

import java.util.ArrayList;
import java.util.Arrays;

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

    // get the item specified at newPath
    try {
      Directory newLocation = (Directory) fileSys.getFile(newPath);
    } catch (CommandException e) {
      Directory newLocation = Move.createFileAtPath(fileSys, newPath);
    }
    
    if (deleteOriginal) {
      // remove this item from its parent directory
      item.getParent().getStoredFiles().remove(item);
    } else {
      // make a copy of the item
      if (item instanceof Directory) {
        item = Move.copyDirectory((Directory) item);
      } else {
        item = Move.copyTextFile((TextFile) item);
      }
    }

    // store the item in this directory
    try {
      Directory newLocation = (Directory) fileSys.getFile(newPath);
      newLocation.storeFile(item);
    } catch (CommandException e) {
      Directory newLocation = Move.createFileAtPath(fileSys, newPath);
      newLocation.storeFile(item);
    }
    


  }

  /**
   * Create a directory that is specified at path
   * 
   * @param fileSys The filesystem being worked with
   * @param path The path of the directory
   */
  private static Directory createFileAtPath(FileSystem fileSys, String path) {

    // get the first existing parent file on the path
    try {
      Directory parent = fileSys.traversePath(path);
      return parent;
    } catch (CommandException e) {
      // cut the last file specified in path
      String parentPath = path.substring(0, path.lastIndexOf("/"));
      String cutPath = path.substring(path.lastIndexOf("/")).trim()
          .substring(1);
      ArrayList<String> unstoredFiles = new ArrayList<String>(
          Arrays.asList(cutPath.split("/")));
      Directory parent = Move.createFileAtPath(fileSys, parentPath, 
          unstoredFiles);
      return parent;
    }
  }
  
  /**
   * Create a directory that is specified at path
   * 
   * @param fileSys The filesystem being worked with
   * @param path The path of the directory
   * @param filesToBeStored The list of files that are yet to be created
   */
  private static Directory createFileAtPath(FileSystem fileSys, String path, 
      ArrayList<String> filesToBeStored) {
    
    if (path.isEmpty()) {
      Directory parent = new Directory(filesToBeStored.get(0));
      fileSys.getRootDirectory().storeFile(parent);
      filesToBeStored.remove(0);
      // store all the files starting under this parent
      Directory curr = parent;
      for (String file : filesToBeStored) {
        Directory next = new Directory(file);
        curr.storeFile(next);
        curr = next;
      }
      return parent;
    }
    
    // get the first existing parent file on the path
    try {
      Directory parent = fileSys.traversePath(path);
      // store all the files starting under this parent
      Directory curr = parent;
      for (String file : filesToBeStored) {
        Directory next = new Directory(file);
        curr.storeFile(next);
        curr = next;
      }
      return parent;
    } catch (CommandException e) {
      // cut the last file specified in path
      String parentPath = path.substring(0, path.lastIndexOf("/") - 1);
      String cutPath = path.substring(path.lastIndexOf("/")).trim();
      ArrayList<String> unstoredFiles = new ArrayList<String>(
          Arrays.asList(cutPath.split("/")));
      Directory parent = Move.createFileAtPath(fileSys, parentPath, 
          unstoredFiles);
      return parent;
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
        copy.storeFile(Move.copyDirectory((Directory) item));
      } else {
        copy.storeFile(Move.copyTextFile((TextFile) item));
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
    TextFile copy =
        new TextFile(file.getName(), ((TextFile) file).getFileContents(),
            file.getParent());
    return copy;
  }
  
  public static void main(String[] args) throws CommandException {
    FileSystem fs = new FileSystem();
    fs.getCurrentDirectory().storeFile(new Directory("a"));
    Move.moveItem(fs, "/a", "/b", false);
  }
}
