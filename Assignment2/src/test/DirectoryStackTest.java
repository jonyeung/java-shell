package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.CommandException;
import driver.Directory;
import driver.DirectoryStack;
import driver.FileSystem;

/**
 * Class for testing the methods in DirectoryStack
 */
public class DirectoryStackTest {

  FileSystem fileSystem;

  /**
   * Sets up a Filesystem that holds directories: user1, user2, and user3 where
   * user1 contains a directory called docs
   */
  @Before
  public void setUp() {

    fileSystem = new FileSystem();
    Directory root = fileSystem.getRootDirectory();
    Directory user1 = new Directory("user1");
    root.storeFile(user1);
    root.storeFile(new Directory("user2"));
    root.storeFile(new Directory("user3"));
    user1.storeFile(new Directory("docs"));
  }

  /**
   * Cleans up the Filesystem and empties the directory stack
   */
  @After
  public void cleanUp() {

    fileSystem = null;
    DirectoryStack.removeSavedDirectories();
  }

  /**
   * Test that pushd adds the directory to the stack
   * 
   * @throws CommandException If trying to change directory to a file that does
   *         not exist
   */
  @Test
  public void testPushd() throws CommandException {

    DirectoryStack.pushd(fileSystem, "/user2");
    DirectoryStack.pushd(fileSystem, "/user1/docs");
    assertEquals(DirectoryStack.numDirectories(), 2);

    String result = fileSystem.getCurrentDirectory().getName();
    String expected = "docs";
    assertEquals(result, expected);
  }

  /**
   * Test that popd returns to the most recently pushed directory
   * 
   * @throws CommandException If trying to change directory to a file that does
   *         not exist, Trying to pop from empty directory stack
   */
  @Test
  public void testPopd() throws CommandException {

    DirectoryStack.pushd(fileSystem, "user2");
    DirectoryStack.pushd(fileSystem, "/user1/docs");
    DirectoryStack.popd(fileSystem);
    assertEquals(DirectoryStack.numDirectories(), 1);

    String result = fileSystem.getCurrentDirectory().getName();
    String expected = "user2";
    assertEquals(result, expected);
  }

  /**
   * Test that popd raises an exception of popping from empty stack
   */
  @Test
  public void testPopdFromEmptyStack() {

    try {
      DirectoryStack.popd(fileSystem);
      fail("Popping from empty stack");
    } catch (CommandException e) {
    }
  }
}
