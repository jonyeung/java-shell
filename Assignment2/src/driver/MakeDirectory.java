package driver;


public class MakeDirectory {

  /**
   * Create a directory given a relative file path
   * 
   * @param curr The directory the user is currently in
   * @param dirName The name of the new directory being made
   */
  public static void mkdirRelative(Directory curr, String dirName) {
    // create a new directory in curr
    curr.storeFile(new Directory(dirName));
  }
  
  /**
   * Create a directory given an absolute file path
   * 
   * @param path The absolute file path of the new directory
   */
  public static void mkdirAbsolute(String path) {
    // split the path into a list
    String[] pathway = Interpreter.filepathToArray(path);
    // get the name of the new directory being made
    String newDir = pathway[-1];
    
    // get the parent directory of the new directory
    String newPath = path.substring(0, path.lastIndexOf("/"));
    Directory parent = (Directory) Tree.traversePath(newPath);
    
    // create a new directory in the parent directory
    parent.storeFile(new Directory(newDir));
  }
  

}
