import java.util.List;

/**
 * Represents a Tree of Object String. Starts as a single root
 * which points to a List<Node<String>> of children.
 */
public class Tree<String>{

  private Node<String> root;

  /**
   * Default Tree constructor
   */
  public Tree(){

    this.super();
  }

  /**
   * Get root of tree
   * @return root
   */
  public Node<String> getRoot() {

    return this.root;
  }

  /**
   * Set the root of tree
   * @return root
   */
  public void setRoot(Node<String> root) {

    this.root = root;
  }

  /**
   * Return List representation of Tree
   * @return treeList
   */
  public List<Node<String>> treeToList() {

    List(Node<String>) treeList = new ArrayList(<Node<String>>();
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
  private void traverse(Node<String> startNode, List<Node<String>> list){

    list.add(startNode);
    for (Node<T> singleNode : startNode.getChildren()){
      this.traverse(startNode, list);
    }
  }
}

/**
 * Represents a node of Tree<String> class.
 */
public class Node<String>{

  public String nodeStorage;
  public List<Node<String>> children;

  /**
   * Default Node constructor
   */
  public Node(){

    this.super();
  }

  /**
   * Get the children of Node<String>
   * @return children The children of Node<String>
   */
  public List<Node<String>> getChildren(){

    if (this.children == null){
      return new ArrayList<Node<String>>();
    }
    return this.chidren;
  }

  /**
   * Set the children of Node<String>
   * @return children The children of Node<String> to set
   */
  public void setChildren(ist<Node<String>> children){

    this.children = children;
  }

  /**
   * Return number of children of the Node<String>
   * @return children The children of Node<String> to set
   */
  public int getNumberOfChildren(List<Node<String>> children){

    int numberOfChildren = 0;

    if (children == null){
      return numberOfChildren;
    }

    numberOfChildren = children.size();
    return numberOfChildren;
  }

  /**
   * Add a child to the list of children for this node.
   * @param child The node object to add
   */
  public void addChild(Node<String> child){

    if (children == null){
      children = new ArrayList<Node<String>>();
    }
 
    children.add(child);
  }

  /**
   * Add a Node<String> at a position in child list
   * @param index The index of insertion
   * @param child the Node<String> object added
   */
  public void addChildAt(int index, Node<String> child) throws IndexOutOfBoundsException{

    if (index == getNumberOfChildren()){
      this.addChild(child);
    }

    else{
      // throw the exception
      children.get(index);
      children.add(index, child);
    }
  }

  /**
   * Remove a Node<String> at a position in child list
   * @param index The index of removal
   */
  public void removeChildAt(int index) throws IndexOutOfBoundsException{

    children.remove(index);
  }

  /**
   * Get the data stored in the Node, specifically the directory path string.
   * @return nodeStorage The string represenation of the directory/file path
   */
  public String getNodeStorage(){

    return this.nodeStorage;
  }

  /**
   * Set the data stored in the Node, the directory path string.
   * @param path The string repr. of the directory/file path to set
   */
  public void setNodeStorage(String path){

    this.nodeStorage = path;
  }

  /**
   * Return String representation of Node
   * @return build The modified string repr. of Node
   */
  public String toString() {

    StringBuilder build = new StringBuilder();
    build.append("{").append(this.getNodeStorage().toString()).append(",[");
    int i = 0;
    for (Node<String> node : this.getChildren()) {
        if (i > 0) {
          build.append(",");
        }
        build.append(node.getNodeStorage().toString());
        i++;
    }
    build.append("]").append("}");
    return build.toString();
  }
}
