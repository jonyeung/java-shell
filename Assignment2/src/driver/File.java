public class File {

  String fileName;
  File parent;

  // constructor with no parent
  public File(String name) {
    fileName = name;
  }

  // constructor with a parent file
  public File(String name, File parentFile) {
    parent = parentFile;
  }


}
