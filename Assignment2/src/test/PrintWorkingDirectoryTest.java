package test;

import org.junit.*;
import static org.junit.Assert.*;

import driver.PrintWorkingDirectory;
import driver.FileSystem;
import driver.Directory;

/**
 * Class for testing the methods in PrintWorkingDirectory
 */
public class PrintWorkingDirectoryTest {

  FileSystem fileSystem;
  String expectedOutput;
  String result;

  /**
   * Sets up the fileSystem and expected/result strings
   */
  @Before
  public void setUp() {

    fileSystem = new FileSystem();
    expectedOutput = "";
    result = "";
  }

  /**
   * Sets the fileSystem and expected/result strings to null
   */
  @After
  public void cleanUp() {

    fileSystem = null;
    expectedOutput = null;
    result = null;
  }

  /**
   * Test printing the root directory
   */
  @Test
  public void testPrintRootDirectory() {

    expectedOutput = "/";
    result = PrintWorkingDirectory.printWD(fileSystem);
    assertEquals(result, expectedOutput);
  }

  /**
   * Test the result of calling PWD on a newly made directory that user cd's
   * into
   */
  @Test
  public void testPrintNewlyMadeDirectory() {

    Directory root = fileSystem.getRootDirectory();
    Directory dir1 = new Directory("dir1", root);
    root.storeFile(dir1);
    fileSystem.setCurrentDirectory(dir1);
    expectedOutput = "/dir1/";
    result = PrintWorkingDirectory.printWD(fileSystem);

    assertEquals(result, expectedOutput);
  }
}
