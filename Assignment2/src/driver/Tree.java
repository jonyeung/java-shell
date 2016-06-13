package driver;
import java.util.ArrayList;
import java.util.List;
import driver.Node;

/**
 * Represents a Tree of Object String. Starts as a single root
 * which points to a List<Node<String>> of children.
 */
public class Tree{

  private Node root;

  /**
   * Default Tree constructor
   */
  public Tree(){

    super();
  }

  /**
   * Get root of tree
   * @return root
   */
  public Node getRoot() {

    return this.root;
  }

  /**
   * Set the root of tree
   * @return root
   */
  public void setRoot(Node root) {

    this.root = root;
  }

  /**
   * Return List representation of Tree
   * @return treeList
   */
  public List<Node> treeToList() {

    List<Node> treeList = new ArrayList<Node>();
    this.traverse(root, treeList);
    return treeList;
  }

  /**
   * Return String representation of Tree
   * @return treeString
   */
  public String toString(){

    String treeString = this.treeToList().toString();
    return treeString;
  }

  /**
   * Traverse tree pre-order.
   * @param startNode The starting node to begin traversal
   * @param list The list of nodes
   */
  private void traverse(Node startNode, List<Node> list){

    list.add(startNode);
    for (Node singleNode : startNode.getChildren()){
      this.traverse(startNode, list);
    }
  }
}
