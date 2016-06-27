package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.CommandException;
import driver.Directory;
import driver.DirectoryStack;
import driver.File;
import driver.FileSystem;
import driver.MakeDirectory;
import driver.PrintWorkingDirectory;

public class DirectoryStackTest {

  FileSystem fileSystem;

  @Before
  public void setUp() throws CommandException {
    fileSystem = new FileSystem();
    Directory root = fileSystem.getRootDirectory();
    Directory user1 = new Directory("user1");
    root.storeFile(user1);
    root.storeFile(new Directory("user2"));
    root.storeFile(new Directory("user3"));
    user1.storeFile(new Directory("docs"));
  }

  @After
  public void cleanUp() {
    fileSystem = null;
    DirectoryStack.removeSavedDirectories();
  }

  @Test
  public void testPushd() throws CommandException {

    // test that pushd adds the directory to the stack
    DirectoryStack.pushd(fileSystem, "/user2");
    DirectoryStack.pushd(fileSystem, "/user1/docs");
    assertEquals(DirectoryStack.numDirectories(), 2);

    String result = fileSystem.getCurrentDirectory().getName();
    String expected = "docs";
    assertEquals(result, expected);
  }

  @Test
  public void testPopd() throws CommandException {

    // test that popd returns to the most recently pushed directory
    DirectoryStack.pushd(fileSystem, "user2");
    DirectoryStack.pushd(fileSystem, "/user1/docs");
    DirectoryStack.popd(fileSystem);
    assertEquals(DirectoryStack.numDirectories(), 1);

    String result = fileSystem.getCurrentDirectory().getName();
    String expected = "user2";
    assertEquals(result, expected);
  }

  @Test
  public void testPopdFromEmptyStack() throws CommandException {

    // test that popd raises an exception of popping from empty stack
    try {
      DirectoryStack.popd(fileSystem);
      fail("Popping from empty stack");
    } catch (CommandException e) {
    }
  }
  
}
