package test;

import static org.junit.Assert.*;

import org.junit.Test;

import driver.Directory;
import driver.File;

public class FileTest {

  @Test
  public void testFileWithNoParent() {
    // test that a file without a parent returns nothing when getParent is
    // called
    File file = new File("my file");
    assertEquals(file.getParent(), null);
  }

  @Test
  public void testFileWithParent() {
    // test that a file with a parent returns that parent file when getParent
    // is called
    Directory parent = new Directory("parent directory");
    File file = new File("my file", parent);
    assertEquals(file.getParent(), parent);
  }

  @Test
  public void testFileHasCorrectName() {
    // test that a file constructed with a given name returns that name when
    // getName is called
    File file = new File("my file");
    assertEquals(file.getName(), "my file");
  }
}
