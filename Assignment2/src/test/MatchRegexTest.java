package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import driver.CommandException;
import driver.Directory;
import driver.FileSystem;
import driver.MatchRegex;
import driver.TextFile;

/**
 * Class to test the methods in MatchRegex class
 */
public class MatchRegexTest {

  FileSystem fileSystem;
  String expected;
  String result;

  @Before
  public void setUp() {

    fileSystem = new FileSystem();
    Directory root = fileSystem.getRootDirectory();
    Directory dir1 = new Directory("dir1", root);
    Directory dir2 = new Directory("dir2", root);
    root.storeFile(new TextFile("doc", "As a doctor, I think we should\n"
        + "do that thing\n" + "You know what thing I'm talking about right?\n"
        + "The thing with the carrot\n" + "like what's up doc.", root));
    root.storeFile(new TextFile("movie", "This is the day we saw that thing\n"
        + "That thing was at the movie\n"
        + "You know about the popcorn and that guy", root));
    root.storeFile(new TextFile("out", "A", root));
    root.storeFile(dir1);
    root.storeFile(dir2);
    dir1.storeFile(new TextFile("secret",
        "my secret is...\n" + "I'm always angry said the Hulk\n"
            + "after walking in to the sunset\n"
            + "and looking all cool and green", dir1));
  }

  /**
   * Test grep works on one file
   * 
   * @throws CommandException If file path does not exist, a directory file path
   *         is given and not recursive
   */
  @Test
  public void testGrep() throws CommandException {

    String[] args = {"\"[A-Z]\"", "out"};
    result = MatchRegex.executeGrep(fileSystem, args);
    expected = "A";
    assertEquals(result, expected);
  }

  /**
   * Test grep works with multiple files
   * 
   * @throws CommandException If file path does not exist, a directory file path
   *         is given and not recursive
   */
  @Test
  public void testGrepMultipleFiles() throws CommandException {

    String[] args = {"\".*the.*\"", "out", "doc", "movie", "dir1/secret"};
    result = MatchRegex.executeGrep(fileSystem, args);
    expected =
        "dir1/secret: I'm always angry said the Hulk\n"
            + "dir1/secret: after walking in to the sunset\n"
            + "doc: The thing with the carrot\n"
            + "movie: This is the day we saw that thing\n"
            + "movie: That thing was at the movie\n"
            + "movie: You know about the popcorn and that guy";
    assertEquals(result, expected);
  }

  /**
   * Test grep works recursively on a directory
   * 
   * @throws CommandException If file path does not exist, a directory file path
   *         is given and not recursive
   */
  @Test
  public void testGrepRecursive() throws CommandException {

    String[] args = {"-r", "\".*the.*\"", ".", "doc"};
    result = MatchRegex.executeGrep(fileSystem, args);
    expected =
        "./doc: The thing with the carrot\n"
            + "./movie: This is the day we saw that thing\n"
            + "./movie: That thing was at the movie\n"
            + "./movie: You know about the popcorn and that guy\n"
            + "./dir1/secret: I'm always angry said the Hulk\n"
            + "./dir1/secret: after walking in to the sunset\n"
            + "doc: The thing with the carrot";
    assertEquals(result, expected);
  }

  /**
   * Test that an exception is thrown if double quotes is not used to surround
   * the regular expression
   */
  @Test
  public void testGrepRegexNoDoubeQuote() {

    try {
      String[] args = {"-r", ".*the.*", "."};
      result = MatchRegex.executeGrep(fileSystem, args);
      fail("Regex is not surrounded by double quote characters");
    } catch (CommandException e) {
    }
  }

  /**
   * Test that an exception is thrown if a directory filepath is given and grep
   * is not called with -r
   */
  @Test
  public void testGrepRegexNotRecursiveWithDirectoryFilePath() {

    try {
      String[] args = {"\".*the.*\"", "."};
      result = MatchRegex.executeGrep(fileSystem, args);
      fail("Cannot grep a directory with -r");
    } catch (CommandException e) {
    }
  }
}
