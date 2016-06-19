package driver;

public class ChangeDirectory {

  /**
   * Change the user's current directory
   * 
   * @param curr The directory the user is currently in
   * @param path The new directory the user changes to
   */
  public void changeCurrentDirectory(Directory curr, String path) {

    // determine if the path is absolute or relative
    // check if path is absolute or relative
    boolean relative = true;

    FileSystem fileSystem = new FileSystem();

    if (path.charAt(0) == '/') {
      relative = false;
    }

    if (relative) {

      // find the child directory
      File file = null;

      for (int i = 0; i < curr.getStoredFiles().size(); i++) {

        file = curr.getStoredFiles().get(i);

        if (file.getName() == path) {
          break;
        }
      }

      // set the current directory to be the child file
      fileSystem.setCurrentDirectory((Directory) file);

    } else {

      // get the file found at the end of the pathway
      File end = fileSystem.traversePath(path);

      // set the current directory to be this file
      fileSystem.setCurrentDirectory((Directory) end);
    }
  }
}
