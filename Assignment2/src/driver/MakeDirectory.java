package driver;

public class MakeDirectory {

  /**
   * Create a directory given a relative file path
   * 
   * @param fileSystem The file system with all the files and directories
   * @param dirName The name of the new directory being made
   */
  public static void mkdirRelative(FileSystem fileSystem, String dirName) {

    // create a new directory in the current directory of the file system
    fileSystem.getCurrentDirectory().storeFile(new Directory(dirName));
  }

  /**
   * Create a directory given an absolute file path
   * 
   * @param fileSystem The file system with all the files and directories
   * @param path The absolute file path of the new directory
   */
  public static void mkdirAbsolute(FileSystem fileSystem, String path) {

    // split the path into a list
    String[] pathway = Interpreter.filepathToArray(path);

    // get the name of the new directory being made
    String newDir = pathway[-1];

    // get the parent directory of the new directory
    String parentPath = path.substring(0, path.lastIndexOf("/"));
    Directory parent = (Directory) fileSystem.traversePath(parentPath);

    // create a new directory in the parent directory
    parent.storeFile(new Directory(newDir));
  }
}
