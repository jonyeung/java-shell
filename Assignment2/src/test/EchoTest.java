package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.CommandException;
import driver.Directory;
import driver.Echo;
import driver.FileSystem;
import driver.TextFile;

/**
 * Class for testing the methods in Echo
 */
public class EchoTest {

  FileSystem fileSystem;
  String expected;
  String result;
  String[] args;

  /**
   * Sets up the fileSystem
   */
  @Before
  public void setUp() {

    fileSystem = new FileSystem();
  }

  /**
   * Sets the fileSystem to null
   */
  @After
  public void cleanUp() {

    fileSystem = null;
  }

  /**
   * Test echo with one argument. It should return the string given
   * 
   * @throws CommandException
   */
  @Test
  public void testEchoString() throws CommandException {

    args = new String[] {"Hi World"};
    result = Echo.executeEcho(fileSystem, args);
    expected = "Hi World";
    assertEquals(result, expected);
  }

  /**
   * Test that echo writes the string to a text file
   * 
   * @throws CommandException
   */
  @Test
  public void testEchoWriteToFile() throws CommandException {

    args = new String[] {"Hi World", ">", "out.txt"};
    result = Echo.executeEcho(fileSystem, args);
    expected = "";
    assertEquals(result, expected);

    Directory root = fileSystem.getRootDirectory();
    TextFile outFile = (TextFile) root.getStoredFiles().get(0);
    result = outFile.getFileContents();
    expected = "Hi World";
    assertEquals(result, expected);
  }

  /**
   * Test that echo appends the string to an existing text file
   * 
   * @throws CommandException
   */
  @Test
  public void testEchoAppendToFile() throws CommandException {

    args = new String[] {"Hi World", ">", "out.txt"};
    Echo.executeEcho(fileSystem, args);
    args = new String[] {"This is my world", ">>", "out.txt"};
    Echo.executeEcho(fileSystem, args);

    Directory root = fileSystem.getRootDirectory();
    TextFile outFile = (TextFile) root.getStoredFiles().get(0);
    result = outFile.getFileContents();
    expected = "Hi World\nThis is my world";
    assertEquals(result, expected);
  }

  /**
   * Test that echo raises an exception if appending to a non-existing file
   * 
   * @throws CommandException
   */
  @Test
  public void testAppendToFakeFile() throws CommandException {

    try {
      args = new String[] {"Hi World", ">>", "out.txt"};
      Echo.executeEcho(fileSystem, args);
      fail("Should not be able to append to a file that does not exist.");
    } catch (CommandException e) {
    }
  }

  /**
   * Test that echo raises an exception if two arguments are given
   * 
   * @throws CommandException
   */
  @Test
  public void testWrongNumberParameters() throws CommandException {

    try {
      args = new String[] {"Hi World", "out.txt"};
      Echo.executeEcho(fileSystem, args);
      fail("Two arguments given when there should either be one or three");
    } catch (CommandException e) {
    }
  }

  /**
   * Test that echo raises an exception if three arguments are given, but second
   * isn't a chevron
   * 
   * @throws CommandException
   */
  @Test
  public void testWrongFormatChevron() throws CommandException {

    try {
      args = new String[] {"Hi World", "->", "out.txt"};
      Echo.executeEcho(fileSystem, args);
      fail("Wrong format");
    } catch (CommandException e) {
    }
  }

}
