package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.CommandException;
import driver.Directory;
import driver.File;
import driver.FileSystem;
import driver.List;
import driver.TextFile;

/**
 * Class for testing the methods in List
 */
public class ListTest {

  FileSystem fileSys;

  /**
   * Sets up the Filesystem
   */
  @Before
  public void setUp() {

    fileSys = new FileSystem();
  }

  /**
   * Cleans up the Filesystem
   */
  @After
  public void cleanUp() {

    fileSys = null;
  }

  /**
   * Test that nothing is returned when list is called on a directory holding no
   * files
   * 
   * @throws CommandException If filepath given to list does not exist
   */
  @Test
  public void testListWithNoDirectories() throws CommandException {

    String[] args = {};
    String output = List.list(fileSys, args);
    assertEquals(output, "");
  }

  /**
   * Test that all the file's contents are returned
   * 
   * @throws CommandException If filepath given to list does not exist
   */
  @Test
  public void testListWithMultipleDirectories() throws CommandException {

    Directory root = fileSys.getRootDirectory();
    root.storeFile(new File("file1"));
    root.storeFile(new File("file2"));
    root.storeFile(new File("file3"));
    String[] args = {};
    String output = List.list(fileSys, args);
    assertEquals(output, "file1\nfile2\nfile3");
  }

  /**
   * Test that all the file's contents are returned at the file found at the
   * given file path
   * 
   * @throws CommandException If filepath given to list does not exist
   */
  @Test
  public void testListWithAbsolutePath() throws CommandException {

    Directory root = fileSys.getRootDirectory();
    Directory dir1 = new Directory("dir1");
    Directory dir2 = new Directory("dir2");
    Directory dir3 = new Directory("dir3");
    Directory file1 = new Directory("file1");
    Directory file2 = new Directory("file2");
    root.storeFile(dir1);
    dir1.storeFile(dir2);
    dir2.storeFile(dir3);
    dir3.storeFile(file1);
    dir3.storeFile(file2);
    String[] path = {"/dir1/dir2/dir3"};
    String output = List.list(fileSys, path);
    assertEquals(output, "/dir1/dir2/dir3:\nfile1\nfile2");

  }

  /**
   * Test that all file contents are shown for every path that is provided
   * 
   * @throws CommandException If filepath given to list does not exist
   */
  @Test
  public void testListWithMultipleAbsolutePaths() throws CommandException {

    Directory root = fileSys.getRootDirectory();
    Directory dir1 = new Directory("dir1");
    Directory dir2 = new Directory("dir2");
    Directory file1 = new Directory("file1");
    Directory file2 = new Directory("file2");
    Directory file3 = new Directory("file3");
    Directory file4 = new Directory("file4");
    root.storeFile(dir1);
    root.storeFile(dir2);
    dir1.storeFile(file1);
    dir1.storeFile(file2);
    dir2.storeFile(file3);
    dir2.storeFile(file4);
    String[] path = {"/dir1", "/dir2"};
    String output = List.list(fileSys, path);
    assertEquals(output, "/dir1:\nfile1\nfile2\n\n/dir2:\nfile3\nfile4");
  }

  /**
   * Test that all file contents are shown for every path that is provided and
   * that file names are formatted properly
   * 
   * @throws CommandException If filepath given to list does not exist
   */
  @Test
  public void testListWithMultipleDirectoriesAndFiles() throws CommandException {

    Directory root = fileSys.getRootDirectory();
    Directory dir1 = new Directory("dir1");
    Directory dir2 = new Directory("dir2");
    TextFile file1 = new TextFile("file1", "abc", dir1);
    TextFile file2 = new TextFile("file2", "hi", root);
    root.storeFile(dir1);
    root.storeFile(dir2);
    dir1.storeFile(file1);
    root.storeFile(file2);
    String[] path = {".", "dir1/file1", "/file2", "dir1"};
    String output = List.list(fileSys, path);
    String expected =
        "/file2 dir1/file1\n\n.:\ndir1\ndir2\nfile2\n\ndir1:\nfile1";
    assertEquals(output, expected);
  }

  /**
   * Test that all file contents are shown when using list recursively with one
   * file paths
   * 
   * @throws CommandException If filepath given to list does not exist
   */
  @Test
  public void testRecursiveListWithOneDirectory() throws CommandException {

    Directory root = fileSys.getRootDirectory();
    Directory dir1 = new Directory("dir1");
    Directory dir2 = new Directory("dir2");
    TextFile file1 = new TextFile("file1", "abc", dir1);
    TextFile file2 = new TextFile("file2", "hi", root);
    root.storeFile(dir1);
    root.storeFile(dir2);
    dir1.storeFile(file1);
    root.storeFile(file2);
    String[] path = {"-R", "."};
    String output = List.list(fileSys, path);
    String expected = ".:\ndir1\ndir2\nfile2\n\n./dir1:\nfile1\n\n./dir2:";
    assertEquals(output, expected);
  }

  /**
   * Test that all file contents are shown when using list recursively with
   * multiple file paths
   * 
   * @throws CommandException If filepath given to list does not exist
   */
  @Test
  public void testRecursiveListWithMultipleDirectories()
      throws CommandException {

    Directory root = fileSys.getRootDirectory();
    Directory dir1 = new Directory("dir1");
    Directory dir2 = new Directory("dir2");
    TextFile file1 = new TextFile("file1", "abc", dir1);
    TextFile file2 = new TextFile("file2", "hi", root);
    root.storeFile(dir1);
    root.storeFile(dir2);
    dir1.storeFile(file1);
    root.storeFile(file2);
    String[] path = {"-r", ".", "dir1", "./dir1/.."};
    String output = List.list(fileSys, path);
    String expected =
        ".:\ndir1\ndir2\nfile2\n\n./dir1:\nfile1\n\n"
            + "./dir1/..:\ndir1\ndir2\nfile2\n\n./dir1/../dir1:\nfile1\n\n"
            + "./dir1/../dir2:\n\n./dir2:\n\ndir1:\nfile1";
    assertEquals(output, expected);
  }

  /**
   * Test that a CommandException is thrown if a directory does not exist
   */
  @Test
  public void testListException() {

    try {
      String[] path = {"/dir1"};
      List.list(fileSys, path);
    } catch (CommandException e) {
    }
  }
}
