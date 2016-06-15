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

    /**
     * Prints the contents of the directory found at the given pathway.
     * 
     * @param String
     */
    //public void printDirectoryContentsGivenPath(){
    //}
  }

  public static void main(String[] args) {

    Directory dir1 = new Directory("my dir");
    File f1 = new File("f1");
    File f2 = new File("f2");
    dir1.storeFile(f1);
    dir1.storeFile(f2);
    listContents(dir1);
  }
}
