package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

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
  String result = "";
  String expected;

  /**
   * Sets up a Filesystem that holds directories: dir1 and dir2 dir1 contains
   * directories called file1 and file2 dir2 contains directories called fileA
   * and fileB and a TextFile fileC
   */
  @Before
  public void setUp() {

    fileSystem = new FileSystem();
    Directory root = fileSystem.getRootDirectory();
    Directory dir1 = new Directory("dir1", root);
    Directory dir2 = new Directory("dir2", root);
    Directory file1 = new Directory("file1", dir1);
    Directory file2 = new Directory("file2", dir1);
    Directory fileA = new Directory("fileA", dir2);
    Directory fileB = new Directory("fileB", dir2);
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
   * @throws CommandException If given a non-existing file path
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
   * @throws CommandException If given a non-existing file path
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
   * @throws CommandException If given a non-existing file path
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
   */
  @Test
  public void testGetFileWithInvalidPath() {

    try {
      testFile = fileSystem.getFile("dir1/sdf");
      fail("The file should not exist");
    } catch (CommandException e) {
    }
  }

  /**
   * Test if getting a file's parent works
   * 
   * @throws CommandException If given a non-existing file path
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
   * @throws CommandException If given a non-existing file path
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
   * @throws CommandException If given a non-existing file path
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
   */
  @Test
  public void testGetFileParentOfRoot() {

    try {
      testFile = fileSystem.getFile("..");
      fail("The root does not have a parent");
    } catch (CommandException e) {
    }
  }

  /**
   * Test if traversing an absolute file path works
   * 
   * @throws CommandException If given a non-existing file path
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
   * @throws CommandException If given a non-existing file path
   */
  @Test
  public void testTraverseRelativeFilePath() throws CommandException {

    testFile = fileSystem.traversePath("dir1/file1");
    result = testFile.getName();
    expected = "file1";
    assertEquals(result, expected);
  }

  /**
   * Test if method recursiveDirectoryList will get the directories and
   * sub-directories given a list of file paths
   * 
   * @throws CommandException If filepaths does not exist
   */
  @Test
  public void testRecursiveDirectoryList() throws CommandException {

    Hashtable<String, File> fm = new Hashtable<String, File>();
    String[] args = {".", "/dir1", "dir2/fileA"};
    fileSystem.recursiveDirectoryList(args, fm);
    ArrayList<String> paths = Collections.list(fm.keys());
    Collections.sort(paths);

    ArrayList<String> expected = new ArrayList<String>();
    expected.add(".");
    expected.add("./dir1");
    expected.add("./dir1/file1");
    expected.add("./dir1/file2");
    expected.add("./dir2");
    expected.add("./dir2/fileA");
    expected.add("./dir2/fileB");

    expected.add("/dir1");
    expected.add("/dir1/file1");
    expected.add("/dir1/file2");

    expected.add("dir2/fileA");
    assertEquals(paths, expected);
  }

  /**
   * Test if method directoryList will get the files given a list of file paths
   * 
   * @throws CommandException If filepaths does not exist
   */
  @Test
  public void testDirectoryList() throws CommandException {

    Hashtable<String, File> fm = new Hashtable<String, File>();
    String[] args = {".", "/dir1", "dir2/fileA"};
    fileSystem.DirectoryList(args, fm);
    ArrayList<String> paths = Collections.list(fm.keys());
    Collections.sort(paths);

    ArrayList<String> expected = new ArrayList<String>();
    expected.add(".");
    expected.add("/dir1");
    expected.add("dir2/fileA");
    assertEquals(paths, expected);
  }

}
