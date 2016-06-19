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
  private static void pushd(String newDir) {
    Directory curr = Tree.getCurrentDirectory();
    // use pwd to get the name of the current directory
    String currFilePath = PrintWorkingDirectory.getFilepath(curr);
    // push the current directory onto the stack
    savedDirectories.add(currFilePath);
    // use cd to change into changeDirectory
    ChangeDirectory.cd(curr, newDir);
  }

  /**
   * Gets the first directory saved onto the stack and changed the current
   * working directory to it
   */
  public static void popd() {

    // pops the first element and use cd
    if (savedDirectories.isEmpty()) {
      // TODO raise exception for popping from empty stack
    } else {
      String filepath = savedDirectories.pop();
      ChangeDirectory.cd(Tree.getCurrentDirectory(), filepath);
    }
  }
}
