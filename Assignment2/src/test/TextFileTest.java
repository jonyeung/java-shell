package test;

import static org.junit.Assert.*;

import org.junit.Test;

import driver.TextFile;

/**
 * Class for testing the methods in TextFile
 */
public class TextFileTest {

  /**
   * Test that the constructor that takes in a string stores that string as the
   * file's contents
   */
  @Test
  public void testConstructorWithContents() {
    TextFile file = new TextFile("file", "string contents");
    assertEquals(file.getFileContents(), "string contents");
  }

  /**
   * Test that write overwrites all the file contents
   */
  @Test
  public void testWriteOverwritesContents() {
    TextFile file = new TextFile("file", "old contents");
    file.write("new contents");
    assertEquals(file.getFileContents(), "new contents");
  }

  /**
   * Test that the string given to write becomes the file contents when
   * performed on a file whose contents are empty
   */
  @Test
  public void testWriteToEmpty() {
    TextFile file = new TextFile("file");
    file.write("new contents");
    assertEquals(file.getFileContents(), "new contents");
  }

  /**
   * Test that the string given to append becomes the file contents when
   * performed on a file whose contents are empty
   */
  @Test
  public void testAppendToEmpty() {
    TextFile file = new TextFile("file");
    file.append("new contents");
    assertEquals(file.getFileContents(), "new contents");
  }

  /**
   * Test that append adds a string to the next line of the file contents
   */
  @Test
  public void testAppendAddsToContents() {
    TextFile file = new TextFile("file", "old contents");
    file.append("new contents");
    assertEquals(file.getFileContents(), "old contents\nnew contents");
  }
}
