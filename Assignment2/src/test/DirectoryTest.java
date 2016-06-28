package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.File;

/**
 * Class that tests the method in DirectoryTest
 */
public class DirectoryTest {

  Directory parent;

  @Before
  public void setUp() {

    parent = new Directory("parent");
  }

  @After
  public void cleanUp() {

    parent = null;
  }

  /**
   * Test that store file puts the given file into the directory's list
   */
  @Test
  public void testStoreFile() {

    File file1 = new File("file1");
    File file2 = new File("file2");
    parent.storeFile(file1);
    parent.storeFile(file2);
    ArrayList<File> output = parent.getStoredFiles();
    ArrayList<File> expected = new ArrayList<File>();
    expected.add(file1);
    expected.add(file2);
    assertEquals(output, expected);
  }

  /**
   * Test that store file stores the files in alphabetical order, regardless of
   * when a file was stored
   */
  @Test
  public void testStoreFileAlphabetical() {

    File file1 = new File("c");
    File file2 = new File("b");
    File file3 = new File("a");
    parent.storeFile(file1);
    parent.storeFile(file2);
    parent.storeFile(file3);
    ArrayList<File> output = parent.getStoredFiles();
    ArrayList<File> expected = new ArrayList<File>();
    expected.add(file3);
    expected.add(file2);
    expected.add(file1);
    assertEquals(output, expected);
  }

  /**
   * Test file in directory in the event where the file is in the directory
   */
  @Test
  public void testFileInDirectoryFileExists() {

    File file1 = new File("file1");
    parent.storeFile(file1);
    assertTrue(parent.fileInDirectory("file1"));
  }

  /**
   * Test file in directory in the event where the file is not in the directory
   */
  @Test
  public void testFileInDirectoryFileDoesNotExists() {

    assertFalse(parent.fileInDirectory("file1"));
  }
}
