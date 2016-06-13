package driver;

public class File {

  String fileName;
  File parent;

  // constructor with no parent
  // TODO add JavaDoc for File Class Constructor
  public File(String name) {
    fileName = name;
  }

  // constructor with a parent file
  //TODO add JavaDoc for File Class Constructor
  public File(String name, File parentFile) {
    parent = parentFile;
  }
}
