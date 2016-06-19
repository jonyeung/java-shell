package driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// TODO UNCOMMENT line below after traverse is complete
// import java.util.Set;

import driver.Node;

/**
 * Represents a Tree of Object String. Starts as a single root which points to a
 * List<Node<String>> of children.
 */
public class FileSystem {

  private Node root;

  /**
   * Default Tree constructor
   */
  public FileSystem() {

    super();
  }

  /**
   * Get root of tree
   * 
   * @return root
   */
  public Node getRoot() {

    return this.root;
  }

  /**
   * Set the root of tree
   * 
   */
  public void setRoot(Node root) {

    this.root = root;
  }

  /**
   * Return List representation of Tree
   * 
   * @return treeList
   */
  public List<Node> treeToList() {

    List<Node> treeList = new ArrayList<Node>();
    this.traverse(root, treeList);
    return treeList;
  }

  /**
   * Return String representation of Tree
   * 
   * @return treeString
   */
  public String toString() {

    String treeString = this.treeToList().toString();
    return treeString;
  }

  /**
   * Traverse tree pre-order.
   * 
   * @param startNode The starting node to begin traversal
   * @param list The list of nodes
   */
  private void traverse(Node startNode, List<Node> list) {

    list.add(startNode);
    for (Node singleNode : startNode.getChildren()) {
      this.traverse(startNode, list);
    }

    /**
     * TODO UNCOMMENT AND COMPLETE Traverse the file system based on the given
     * path.
     * 
     * @param path The pathway the file system is to traverse.
     * @return file The file found at the end of the path.
     */
    // public File traversePath(String path) {
    // // parse the path by converting it to a list
    // String[] pathway = Interpreter.filepathToArray(path);
    // // make sure root is in the path
    // if (root.getName() != pathway[0]) {
    // // TODO THROW AN EXCEPTION
    // }
    // else {
    // // traverse to the last file in the path
    // Directory curr = root;
    // for (int i=1; i < pathway.length; i++) {
    // // check if the next file in the path is a child of the current directory
    // ArrayList<File> storedFiles = curr.getStoredFiles();
    //
    // }
    // }
    //
    // }
  }

  private Directory rootDirectory = new Directory("root");
  private Directory currentDirectory = rootDirectory;

  /**
   * Get the root directory
   * 
   */
  public Directory getRootDirectory() {
    return rootDirectory;
  }

  /**
   * Get the current working directory
   * 
   */
  public Directory getCurrentDirectory() {
    return currentDirectory;
  }

  /**
   * Set the current working directory
   * 
   * @param currentDirectory The directory that we want to change to
   */
  public void setCurrentDirectory(Directory currentDirectory) {
    this.currentDirectory = currentDirectory;
  }

  /**
   * Returns a file using the pathway and starts the search in directory curr
   * 
   * @param curr The starting directory to look for file in pathway
   * @param pathway The array of file/directory names to find a file
   * @return File The file that we are looking for
   */
  private File getFile(Directory curr, String[] pathway) {

    File returnFile = null;

    // if no pathway is given, then return curr
    if (pathway.length == 0) {

      returnFile = curr;
    } else {

      // loop through each file in curr and check if any match the name of
      // the first directory in pathway
      int i = 0;
      boolean notFound = true;

      ArrayList<File> storedFiles = curr.getStoredFiles();

      String searchDir = pathway[0];
      String newPathway[] = Arrays.copyOfRange(pathway, 1, pathway.length);

      // check if search directory is '..', then search if parent exists
      // TODO I think it should be !curr.equals(rootDirectory)
      if (searchDir == ".." && !curr.equals(rootDirectory)) {

        returnFile = this.getFile(curr.getParent(), newPathway);
      } else {

        // TODO raise error because no parent
      }

      while (i < storedFiles.size() && notFound) {

        File file = storedFiles.get(i);

        // if the directory is found, then search the next thing on pathway
        if (file.getName().equals(searchDir) && file instanceof Directory) {
          notFound = false;
          returnFile = this.getFile((Directory) file, newPathway);
        }
      }

      if (notFound) {
        // TODO raise error because the directory was not found
      }
    }
    return returnFile;
  }

  /**
   * Returns a file using the file path given
   * 
   * @param path The file path in the format given by the user
   * @return File The file that we are looking for
   */
  public File traversePath(String path) {

    // parse the path by converting it to a list
    String[] pathway = Interpreter.filepathToArray(path);

    // check if path is absolute or relative
    boolean relative = true;

    if (path.charAt(0) == '/') {
      relative = false;
    }

    File returnFile;
    // searches the correct directory for the file in pathway
    if (relative) {
      returnFile = this.getFile(currentDirectory, pathway);
    } else {
      returnFile = this.getFile(rootDirectory, pathway);
    }
    return returnFile;
  }

}
