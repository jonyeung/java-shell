package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Cat;
import driver.CommandException;
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
   * 
   * @throws CommandException
   */
  @Test
  public void testCatOneFile() throws CommandException {

    args = new String[] {"work"};
    result = Cat.cat(fileSystem, args);
    expected = "abc123";
    assertEquals(result, expected);
  }

  /**
   * Test if calling cat with multiple text files will work
   * 
   * @throws CommandException
   */
  @Test
  public void testCatMultipleFile() throws CommandException {

    args = new String[] {"work", "doc", "secret"};
    result = Cat.cat(fileSystem, args);
    expected = "abc123\n\n\n\nHi world\n\n\n\nmy secret is...";
    assertEquals(result, expected);
  }

  /**
   * Test if calling cat with a non-existing file will raise an exception
   * 
   * @throws CommandException
   */
  @Test
  public void testCatWithFakeFile() throws CommandException {

    try {
      args = new String[] {"asf"};
      result = Cat.cat(fileSystem, args);
      fail("The file should not exist");
    } catch (CommandException e) {
    }
  }
}
