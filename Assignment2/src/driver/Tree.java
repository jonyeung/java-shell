package driver;

import java.util.ArrayList;
import java.util.List;
// TODO UNCOMMENT line below after traverse is complete
//import java.util.Set;

import driver.Node;

/**
 * Represents a Tree of Object String. Starts as a single root which points to a
 * List<Node<String>> of children.
 */
public class Tree {

  private Node root;

  /**
   * Default Tree constructor
   */
  public Tree() {

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
   * @return root
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

    /** TODO UNCOMMENT AND COMPLETE
     * Traverse the file system based on the given path.
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
}
