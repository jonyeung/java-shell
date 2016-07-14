package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.ChangeDirectory;
import driver.CommandException;
import driver.FileSystem;
import driver.Directory;

/**
 * Class to test the ChangeDirectory method
 */
public class ChangeDirectoryTest {

  FileSystem fileSystem;
  String expectedResult;
  String actualResult;

  /**
   * Sets up the fileSystem and expected/actual result strings
   */
  @Before
  public void setUp() {

    expectedResult = "";
    actualResult = "";
    fileSystem = new FileSystem();
  }

  /**
   * Sets the fileSystem and expected/actual result strings to null
   */
  @After
  public void tearDown() {

    fileSystem = null;
    actualResult = null;
    expectedResult = null;
  }

  /**
   * Testing changing into directory before the root
   */
  @Test
  public void testChangingToInvalidRoot() {

    try {
      ChangeDirectory.changeCurrentDirectory(fileSystem, "..");
      fail("You are currently at the root directory.");
    } catch (CommandException e) {
    }
  }

  /**
   * Testing changing into invalid directory
   */
  @Test
  public void testChangingToInvalidDirectory() {

    try {
      ChangeDirectory.changeCurrentDirectory(fileSystem, "invalidName");
      fail("Directory invalidName does not exist.");
    } catch (CommandException e) {
    }
  }

  /**
   * Testing changing into valid directory
   * 
   * @throws CommandException If trying to change directory to a file that does
   *         not exist
   */
  @Test
  public void testChangingToValidDirectory() throws CommandException {

    Directory validFile = new Directory("validDir");
    fileSystem.getRootDirectory().storeFile(validFile);
    ChangeDirectory.changeCurrentDirectory(fileSystem, "validDir");
    expectedResult = "/validDir/";
    actualResult = "/" + fileSystem.getCurrentDirectory().getName() + "/";
    assertEquals(actualResult, expectedResult);
  }
}
