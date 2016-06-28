package test;

import org.junit.*;
import static org.junit.Assert.*;

import driver.FileSystem;
import driver.CommandException;
import driver.MakeDirectory;
import driver.Directory;

/**
 * Class to test the methods in MakeDirectory
 */
public class MakeDirectoryTest {

  FileSystem fileSystem;
  Directory directory;

  /**
   * Sets up the directory and FileSystem
   */
  @Before
  public void setUp() {
    fileSystem = new FileSystem();
    directory = new Directory("root");

  }

  /**
   * Sets the directory to null
   */
  @After
  public void cleanUp() {
    directory = null;
  }


  /**
   * Test that makeADirectory creates a directory in the root
   * 
   * @throws CommandException
   */
  @Test
  public void testMakeADirectoryRoot() throws CommandException {
    MakeDirectory.makeADirectory(fileSystem, "dir1");
    Directory root = fileSystem.getRootDirectory();
    assertTrue(root.fileInDirectory("dir1"));
  }

  /**
   * Test that makeADirectory creates a directory in the file specified at the
   * given absolute pathway
   * 
   * @throws CommandException
   */
  @Test
  public void testMakeADirectoryAbsolute() throws CommandException {
    Directory root = fileSystem.getRootDirectory();
    Directory user1 = new Directory("user1");
    Directory dir1 = new Directory("dir1");
    root.storeFile(user1);
    user1.storeFile(dir1);
    MakeDirectory.makeADirectory(fileSystem, "/user1/dir1/doc1");
    assertTrue(dir1.fileInDirectory("doc1"));

  }
}
