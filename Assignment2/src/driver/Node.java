package driver;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node of Tree<String> class.
 */
public class Node{

  public String nodeStorage;
  public List<Node> children;

  /**
   * Default Node constructor
   */
  public Node(){

    super();
  }

  /**
   * Get the children of Node<String>
   * @return children The children of Node<String>
   */
  public List<Node> getChildren(){

    if (this.children == null){
      return new ArrayList<Node>();
    }
    return this.children;
  }

  /**
   * Set the children of Node<String>
   * @return children The children of Node<String> to set
   */
  public void setChildren(List<Node> children){

    this.children = children;
  }

  /**
   * Return number of children of the Node<String>
   * @return children The children of Node<String> to set
   */
  public int getNumberOfChildren(List<Node> children){

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
  public void addChild(Node child){

    if (children == null){
      children = new ArrayList<Node>();
    }
 
    children.add(child);
  }

  /**
   * Add a Node<String> at a position in child list
   * @param index The index of insertion
   * @param child the Node<String> object added
   */
  public void addChildAt(int index, Node child) throws IndexOutOfBoundsException{

    if (index == this.getNumberOfChildren(children)){
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
   * @return nodeStorage The string representation of the directory/file path
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
    for (Node node : this.getChildren()) {
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
