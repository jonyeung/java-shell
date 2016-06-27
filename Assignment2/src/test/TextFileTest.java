package test;

import static org.junit.Assert.*;

import org.junit.Test;

import driver.TextFile;

public class TextFileTest {

  @Test
  public void testConstructorWithContents() {
    // test that the constructor that takes in a string stores that string as
    // the file's contents
    TextFile file = new TextFile("file", "string contents");
    assertEquals(file.getFileContents(), "string contents");
  }

  @Test
  public void testWriteOverwritesContents() {
    // test that write overwrites all the file contents
    TextFile file = new TextFile("file", "old contents");
    file.write("new contents");
    assertEquals(file.getFileContents(), "new contents");
  }

  @Test
  public void testWriteToEmpty() {
    // test that the string given to write becomes the file contents
    TextFile file = new TextFile("file");
    file.write("new contents");
    assertEquals(file.getFileContents(), "new contents");
  }

  @Test
  public void testAppendToEmpty() {
    // test that the string given to append becomes the file contents
    TextFile file = new TextFile("file");
    file.append("new contents");
    assertEquals(file.getFileContents(), "new contents");
  }

  @Test
  public void testAppendAddsToContents() {
    // test that append adds a new line to the file contents
    TextFile file = new TextFile("file", "old contents");
    file.append("new contents");
    assertEquals(file.getFileContents(), "old contents\nnew contents");
  }
}
