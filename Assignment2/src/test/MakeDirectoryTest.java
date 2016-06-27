package test;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import driver.FileSystem;
import driver.CommandException;
import driver.MakeDirectory;
import driver.Directory;
import driver.File;

/**
 * Class to test the methods in MakeDirectory
 */
public class MakeDirectoryTest {

  Directory directory;

  /**
   * Sets up the directory
   */
  @Before
  public void setUp() {
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
   * MODIFY THIS JAVA DOC
   * @throws CommandException
   */
  @Test
  public void testNoDirectoriesExceptRoot() throws CommandException {

    String expectedDirectory = directory.getName();

    FileSystem fileSystem = new FileSystem();
    String actualDirectory = fileSystem.getCurrentDirectory().getName();

    assertEquals(expectedDirectory, actualDirectory);
  }

  /**
   * MODIFY THIS JAVA DOC
   * @throws CommandException
   */
  @Test
  public void testMakeUser1Directory() throws CommandException {

    // TODO write line of code below that adds a user1 dir to expected dir


    ArrayList<File> expected = directory.getStoredFiles();

    FileSystem fileSystem = new FileSystem();
    MakeDirectory.makeADirectory(fileSystem, "user1");
    ArrayList<File> actual = fileSystem.getCurrentDirectory().getStoredFiles();

    assertEquals(expected, actual);
  }
}
