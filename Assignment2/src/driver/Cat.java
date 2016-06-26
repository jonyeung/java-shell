package driver;

public class Cat {

  public static void cat(FileSystem fileSystem, String[] filepaths)
      throws CommandException {

    int count = 1;
    
    for (String path : filepaths) {
      // get the file at path
      File currFile = fileSystem.getFile(path);
      
      // print the contents of currFile
      if (currFile instanceof TextFile) {
        if (count == 1) {
          System.out.println("\n\n");
        }
        System.out.println(((TextFile) currFile).getFileContents());
      }
      count++;
    }
  }


}
