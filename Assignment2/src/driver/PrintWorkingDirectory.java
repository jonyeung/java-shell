package driver;

public class PrintWorkingDirectory {

  /**
   * Print the pathway of the current working directory
   * 
   * @param file The file who's working directory is to be printed.
   */
  public static void printWD(File file) {

    // put the pathway string together starting with the current file
    String pathway = "/" + file.getName();

    // add to the pathway all the parents of the current file
    File curr = file;

    while (curr.getParent() != null) {
      curr = curr.getParent();
      pathway = "/" + curr.getName() + pathway;
    }

    // print out the path
    System.out.println(pathway);
  }

  public static void main(String[] args) {
    Directory dir1 = new Directory("my dir");
    File f1 = new File("f1");
    dir1.storeFile(f1);
    printWD(f1);
  }
}
