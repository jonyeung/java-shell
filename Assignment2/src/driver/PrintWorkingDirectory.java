package driver;

/**
 * This class determines what the current working directory is so that the
 * JShell can print it upon request of the user.
 */
public class PrintWorkingDirectory {

  /**
   * Finds and returns the absolute path of curr.
   * 
   * @param fileSystem The file system with all the files and directories
   * @param curr The current file that we want the absolute path of
   * @return String The pathway of the current working directory
   */
  public static String getAbsoluteFilepath(FileSystem fileSystem, File curr) {

    // Put the pathway string together starting with the current file
    String pathway = "/";

    // Add to the pathway all the parents of the current file
    while (curr.getParent() != null) {
      pathway = "/" + curr.getName() + pathway;
      curr = curr.getParent();
    }

    return pathway;
  }
  
  /**
   * Finds and returns the current working directory
   * 
   * @param fileSystem The file system with all the files and directories
   * @return String The pathway of the current working directory
   */
  public static String printWD(FileSystem fileSystem) {
    
    return getAbsoluteFilepath(fileSystem, fileSystem.getCurrentDirectory());
  }
}
