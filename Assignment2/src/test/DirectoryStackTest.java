package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.CommandException;
import driver.DirectoryStack;
import driver.FileSystem;
import driver.MakeDirectory;
import driver.PrintWorkingDirectory;

public class DirectoryStackTest {

  FileSystem fileSystem;

  @Before
  public void setUp() throws CommandException {
    fileSystem = new FileSystem();
    String[] commandArgs = {"user1", "user2", "user3", "user1/docs"};
    for (int i = 0; i < commandArgs.length; i++) {
      MakeDirectory.makeADirectory(fileSystem, commandArgs[i]);
    }
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

    String result = PrintWorkingDirectory.printWD(fileSystem);
    String expected = "/user1/docs/";
    assertEquals(result, expected);
  }

  @Test
  public void testPopd() throws CommandException {

    // test that popd returns to the most recently pushed directory
    DirectoryStack.pushd(fileSystem, "user2");
    DirectoryStack.pushd(fileSystem, "/user1/docs");
    DirectoryStack.popd(fileSystem);

    String result = PrintWorkingDirectory.printWD(fileSystem);
    String expected = "/user2/";
    assertEquals(DirectoryStack.numDirectories(), 1);
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
