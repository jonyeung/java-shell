package driver;

public class PrintWorkingDirectory {

  /**
   * Returns the pathway of the current working directory
   * 
   * @param fileSystem The file system with all the files and directories
   * @return String
   */
  public static String printWD(FileSystem fileSystem) {
    // get the current directory
    Directory file = fileSystem.getCurrentDirectory();
    // put the pathway string together starting with the current file
    String pathway = "/";

    // add to the pathway all the parents of the current file
    File curr = file;

    while (curr.getParent() != null) {
      pathway = "/" + curr.getName() + pathway;
      curr = curr.getParent();
    }

    return pathway;
  }

}
