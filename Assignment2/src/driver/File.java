package driver;

/**
 * The File Class. This class consists of 2 constructors, the first taking in
 * only a name <type str> parameter and the second taking in a name <type str>
 * and a parentFile <type Directory> as parameters. This class also consists of
 * accessor methods to get parent, set parent and get name.
 */
public class File implements Comparable<File> {

  private String fileName;
  private Directory parent;

  /**
   * File constructor with no parent.
   * 
   * @param name The name of the file.
   */
  public File(String name) {

    this.fileName = name;
    this.parent = null;
  }

  /**
   * File constructor with a parent file
   * 
   * @param name The name of the file.
   * @param parentFile The parent of this file.
   */
  public File(String name, Directory parentFile) {

    this.fileName = name;
    this.parent = parentFile;
  }

  /**
   * Getter for parent file.
   * 
   * @return Directory The parent file.
   */
  public Directory getParent() {

    return parent;
  }

  /**
   * Setter for parent file.
   * 
   * @param parent The parent of the file.
   */
  public void setParent(Directory parent) {

    this.parent = parent;
  }

  /**
   * Getter for name of the file.
   * 
   * @return String The name of the file.
   */
  public String getName() {

    return fileName;
  }

  /**
   * Allows comparisons between files by comparing their names
   * 
   * @param otherFile The second file we want to compare to
   * @return int Returns a negative number if curr file occurs before and
   *         positive if curr is after. The number value will be how far apart
   *         the two file names are alphabetically
   */
  @Override
  public int compareTo(File otherFile) {

    return this.getName().compareTo(otherFile.getName());
  }
}
