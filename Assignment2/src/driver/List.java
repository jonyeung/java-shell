package driver;

public class List {


  /**
   * Prints the contents of the current directory.
   * 
   * @param dir The current directory who's contents are to be printed
   */
  public static void listContents(Directory dir) {
    // print all the elements in the storedFiles set in dir
    for (File file : dir.getStoredFiles()) {
      System.out.println(file.getName());
    }
    
  /**TODO
   * Prints the contents of the directory found at the given pathway.
   * 
   * @param String
   */
  }
}
