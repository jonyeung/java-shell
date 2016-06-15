package driver;

import java.util.Stack;

public class DirectoryStack {

  private static Stack<String> savedDirectories = new Stack<String>();

  /**
   * Puts the current working directory onto the stack of directories and
   * changes the current working directory to the one specified
   * 
   * @param changeDirectory The current directory will be changed to this
   */
  private static void pushd(String changeDirectory) {
    // use pwd to get the name of the current directory

    // push the current directory onto the stack

    // use cd to change into changeDirectory
  }

  /**
   * Gets the first directory saved onto the stack and changed the current
   * working directory to it
   */
  public static void popd() {
    // pops the first element and use cd
    // if there is no directory on the stack then raise exception

  }



}
