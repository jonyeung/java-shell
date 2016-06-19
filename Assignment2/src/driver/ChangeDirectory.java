package driver;

public class ChangeDirectory {

  /**
   * Change the user's current directory
   * 
   * @param curr The directory the user is currently in
   * @param path The new directory the user changes to
   */
  public static void cd(Directory curr, String path) {
    // determine if the path is absolute or relative
    // check if path is absolute or relative
    boolean relative = true;
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
      FileSystem.setCurrentDirectory((Directory) file);
    } else {
      // get the file found at the end of the pathway
      File end = FileSystem.traversePath(path);
      // set the current directory to be this file
      FileSystem.setCurrentDirectory((Directory) end);
    }
  }
}
