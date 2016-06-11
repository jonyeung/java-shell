import java.util.Set;


public class Directory extends File{

  String directoryName;
  Set<File> storedFiles;
  
  public Directory(String name) {
    super(name);
  }
  
  /**
   * Adds a file to the current directory.
   * @param file The file to be stored in the directory.
   */
  
  public void storeFile(File file) {
    // add the file to the set of stored files
    storedFiles.add(file);
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
