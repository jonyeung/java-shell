/**
 * Represents a Tree of Object of type String. Starts as a single root
 * which points to a List<Node<String>> of children.
 */
public class Tree<String>{

  private Node<String> root;

  /**
   * Default constructor
   */
  public Tree(){
    this.super();
  }

  /**
   * Get root Node of tree
   * @return root
   */
  public Node<String> getRoot() {
    return this.root;
  }

  /**
   * Set the root Node of tree
   * @return root
   */
  public void setRoot(Node<String> root) {
    this.root = root;
  }

  /**
   * List Representation of the Tree
   * @return treeList
   */
  public List<Node<String>> treeToList() {
    List(Node<String>) treeList = new ArrayList(<Node<String>>();
    this.traverse(root, treeList);
    return treeList;
  }

  /**
   * String Represenation of the Tree
   * @return string
   */
  public String toString(){
    String treeString = this.treeToList().toString();
    return treeString;
  }

  /**
   * Traverses tree Pre-order.
   * @param startNode The starting node to begin traversal
   * @param list The starting list of nodes
   */
  private void traverse(Node<String> startNode, List<Node<String>> list){
    list.add(startNode);
    for (Node<T> singleNode : startNode.getChil){
      this.traverse(startNode, list);
    }
  }
}
