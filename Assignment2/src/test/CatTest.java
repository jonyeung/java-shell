package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Cat;
import driver.Directory;
import driver.FileSystem;
import driver.TextFile;

/**
 * Class for testing the methods in Cat
 */
public class CatTest {

  FileSystem fileSystem;
  String result;
  String expected;
  String[] args = null;

  /**
   * Sets up the fileSystem containing three text files: doc, work and secret
   */
  @Before
  public void setUp() {

    fileSystem = new FileSystem();
    Directory root = fileSystem.getRootDirectory();
    root.storeFile(new TextFile("doc", "Hi world", root));
    root.storeFile(new TextFile("work", "abc123", root));
    root.storeFile(new TextFile("secret", "my secret is...", root));
  }

  /**
   * Sets the fileSystem to null
   */
  @After
  public void cleanUp() {

    fileSystem = null;
  }

  /**
   * Test if calling cat with one text file will work
   */
  @Test
  public void testCatOneFile() {

    args = new String[] {"work"};
    result = Cat.cat(fileSystem, args);
    expected = "work:\nabc123";
    assertEquals(result, expected);
  }

  /**
   * Test if calling cat with multiple text files will work
   */
  @Test
  public void testCatMultipleFile() {

    args = new String[] {"work", "doc", "secret"};
    result = Cat.cat(fileSystem, args);
    expected = "work:\nabc123\n\ndoc:\nHi world\n\nsecret:\nmy secret is...";
    assertEquals(result, expected);
  }

  /**
   * Test if calling cat with a non-existing file will give appropriate output
   */
  @Test
  public void testCatWithFakeFile() {

    args = new String[] {"asf"};
    result = Cat.cat(fileSystem, args);
    expected = "asf is not a file.";
    assertEquals(result, expected);
  }

  /**
   * Test if calling cat with non-existing and existing file will give
   * appropriate output
   */
  @Test
  public void testCatWithFakeAndRealFiles() {

    args = new String[] {"asf", "work", "123", "doc", "wok"};
    result = Cat.cat(fileSystem, args);
    expected = "asf is not a file.\n\n" + "work:\nabc123\n\n"
        + "123 is not a file.\n\n" + "doc:\nHi world\n\n"
        + "wok is not a file.";
    assertEquals(result, expected);
  }
}
