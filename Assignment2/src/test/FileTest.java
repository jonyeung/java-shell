package test;

import static org.junit.Assert.*;

import org.junit.Test;

import driver.Directory;
import driver.File;

/**
 * Class for testing the methods in file
 */
public class FileTest {

  /**
   * Test that a file without a parent returns nothing when getParent is called
   */
  @Test
  public void testFileWithNoParent() {

    File file = new File("my file");
    assertEquals(file.getParent(), null);
  }

  /**
   * Test that a file with a parent returns that parent file when getParent is
   * called
   */
  @Test
  public void testFileWithParent() {

    Directory parent = new Directory("parent directory");
    File file = new File("my file", parent);
    assertEquals(file.getParent(), parent);
  }

  /**
   * Test that a file constructed with a given name returns that name when
   * getName is called
   */
  @Test
  public void testFileHasCorrectName() {

    File file = new File("my file");
    assertEquals(file.getName(), "my file");
  }
}
