package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.CommandException;
import driver.Directory;
import driver.File;
import driver.FileSystem;
import driver.TextFile;

/**
 * Class for testing the methods in FileSystem
 */
public class FileSystemTest {

  FileSystem fileSystem;
  File testFile;
  String result;
  String expected;

  /**
   * Sets up the fileSystem with multiple directories and files
   */
  @Before
  public void setUp() {
    fileSystem = new FileSystem();
    Directory root = fileSystem.getRootDirectory();
    Directory dir1 = new Directory("dir1");
    Directory dir2 = new Directory("dir2");
    Directory file1 = new Directory("file1");
    Directory file2 = new Directory("file2");
    Directory fileA = new Directory("fileA");
    Directory fileB = new Directory("fileB");
    root.storeFile(dir1);
    root.storeFile(dir2);
    dir1.storeFile(file1);
    dir1.storeFile(file2);
    dir2.storeFile(fileA);
    dir2.storeFile(fileB);
    dir2.storeFile(new TextFile("fileC", "Hi world", dir2));
  }

  /**
   * Sets the fileSystem to null
   */
  @After
  public void cleanUp() {
    fileSystem = null;
  }

  /**
   * Test if getting a text file works
   * 
   * @throws CommandException
   */
  @Test
  public void testGetTextFile() throws CommandException {

    testFile = fileSystem.getFile("/dir2/fileB");
    result = testFile.getName();
    expected = "fileB";
    assertEquals(result, expected);
  }

  /**
   * Test if getting a directory works if you use '.' characters
   * 
   * @throws CommandException
   */
  @Test
  public void testGetFileWithCurrentCharacter() throws CommandException {

    testFile = fileSystem.getFile("/dir1/././");
    result = testFile.getName();
    expected = "dir1";
    assertEquals(result, expected);
  }

  /**
   * Test if getting a directory works if you use '..' characters
   * 
   * @throws CommandException
   */
  @Test
  public void testGetFileWithParentCharacter() throws CommandException {

    testFile = fileSystem.getFile("/dir1/../dir2");
    result = testFile.getName();
    expected = "dir2";
    assertEquals(result, expected);
  }

  /**
   * Test if it throws an exception if a fake file path is given
   * 
   * @throws CommandException
   */
  @Test
  public void testGetFileWithInvalidPath() throws CommandException {

    try {
      testFile = fileSystem.getFile("dir1/sdf");
      fail("The file should not exist");
    } catch (CommandException e) {
    }
  }

  /**
   * Test if getting a file's parent works
   * 
   * @throws CommandException
   */
  @Test
  public void testGetParent() throws CommandException {

    testFile = fileSystem.getParentDirectory("/dir2/fileB");
    result = testFile.getName();
    expected = "dir2";
    assertEquals(result, expected);
  }

  /**
   * Test if getting a file's parent works when using the '.' character
   * 
   * @throws CommandException
   */
  @Test
  public void testGetParentWithCurrentCharacter() throws CommandException {

    testFile = fileSystem.getParentDirectory("/dir2/./fileA");
    result = testFile.getName();
    expected = "dir2";
    assertEquals(result, expected);
  }

  /**
   * Test if getting a file's parent works when using the '..' character
   * 
   * @throws CommandException
   */
  @Test
  public void testGetParentWithParentCharacter() throws CommandException {

    testFile = fileSystem.getParentDirectory("/dir1/file1/../file2/");
    result = testFile.getName();
    expected = "dir1";
    assertEquals(result, expected);
  }

  /**
   * Test if getting the parent of the root directory will raise an exception
   * 
   * @throws CommandException
   */
  @Test
  public void testGetFileParentOfRoot() throws CommandException {

    try {
      testFile = fileSystem.getFile("..");
      fail("The root does not have a parent");
    } catch (CommandException e) {
    }
  }

  /**
   * Test if traversing an absolute file path works
   * 
   * @throws CommandException
   */
  @Test
  public void testTraverseAbsoluteFilePath() throws CommandException {

    testFile = fileSystem.traversePath("/dir1/file1");
    result = testFile.getName();
    expected = "file1";
    assertEquals(result, expected);
  }

  /**
   * Test if traversing a relative file path works
   * 
   * @throws CommandException
   */
  @Test
  public void testTraverseRelativeFilePath() throws CommandException {

    testFile = fileSystem.traversePath("dir1/file1");
    result = testFile.getName();
    expected = "file1";
    assertEquals(result, expected);
  }

}
