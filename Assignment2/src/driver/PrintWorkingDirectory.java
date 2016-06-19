package driver;

public class PrintWorkingDirectory {

  /**
   * Returns the pathway of the current working directory
   * 
   * @param file The file who's working directory is to be returned.
   * @return String 
   */
  public static String getFilepath(File file) {

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
   */
  public static void printWD() {

    // print out the path
    System.out.println(getFilepath(Tree.getCurrentDirectory()));
  }
  
  
  
  public static void main(String[] args) {
    Directory dir1 = new Directory("my dir");
    File f1 = new File("f1");
    dir1.storeFile(f1);
    printWD();
  }
}
