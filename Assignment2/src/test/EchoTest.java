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

public class EchoTest {

  FileSystem fileSystem;
  String expected;
  String result;
  String[] args;

  @Before
  public void setUp() throws CommandException {
    fileSystem = new FileSystem();
  }

  @After
  public void cleanUp() {
    fileSystem = null;
  }

  @Test
  public void testEchoString() throws CommandException {

    // test echo with one argument. It should return the string given
    args = new String[] {"Hi World"};
    result = Echo.executeEcho(fileSystem, args);
    expected = "Hi World";
    assertEquals(result, expected);
  }

  @Test
  public void testEchoWriteToFile() throws CommandException {

    // test that echo writes the string to a textfile
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

  @Test
  public void testEchoAppendToFile() throws CommandException {

    // test that echo appends the string to an existing textfile
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

  @Test
  public void testAppendToFakeFile() throws CommandException {

    // test that echo raises an exception if appending to a non-existing file
    try {
      args = new String[] {"Hi World", ">>", "out.txt"};
      Echo.executeEcho(fileSystem, args);
      fail("Should not be able to append to a file that does not exist.");
    } catch (CommandException e) {
    }
  }

  @Test
  public void testWrongNumberParameters() throws CommandException {

    // test that echo raises an exception if two arguments are given
    try {
      args = new String[] {"Hi World", "out.txt"};
      Echo.executeEcho(fileSystem, args);
      fail("Two arguments given when there should either be one or three");
    } catch (CommandException e) {
    }
  }

  @Test
  public void testWrongFormatChevron() throws CommandException {

    // test that echo raises an exception if three arguments are given, but
    // second isn't a chevron
    try {
      args = new String[] {"Hi World", "->", "out.txt"};
      Echo.executeEcho(fileSystem, args);
      fail("Wrong format");
    } catch (CommandException e) {
    }
  }

}
