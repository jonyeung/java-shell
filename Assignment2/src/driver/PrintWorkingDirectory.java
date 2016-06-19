package driver;

public class PrintWorkingDirectory {

  /**
   * Returns the pathway of the current working directory
   * 
   * @param fileSystem The file system with all the files and directories
   * @return String
   */
  public static String getFilepath(FileSystem fileSystem) {
    // get the current directory
    Directory file = fileSystem.getCurrentDirectory();
    // put the pathway string together starting with the current file
    String pathway = "/" + file.getName();

    // add to the pathway all the parents of the current file
    File curr = file;

    while (curr.getParent() != null) {
      curr = curr.getParent();
      pathway = "/" + curr.getName() + pathway;
    }

    return pathway;
  }

  /**
   * Print the pathway of the current working directory
   * 
   * @param fileSystem The file system with all the files and directories
   */
  public static void printWD(FileSystem fileSystem) {

    // print out the path
    System.out.println(getFilepath(fileSystem));
  }


}
