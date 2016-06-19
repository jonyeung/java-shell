package driver;

public class List {

  /**
   * Prints the contents of the current directory.
   * 
   * @param dir The current directory who's contents are to be printed
   */
  public static void listContents(Directory dir) {

    // Print all the elements in the storedFiles set in dir
    for (File file : dir.getStoredFiles()) {
      System.out.println(file.getName());
    }
  }

  /**
   * Prints the contents of the directory found at the given pathway.
   * 
   * @param path The pathway specifying a certain file
   */
  public static void printDirectoryContentsGivenPath(String path) {
    // reach the file found at the pathway
    Directory file = (Directory) Tree.traversePath(path);
    // list the contents of this file
    List.listContents(file);
  }

}
