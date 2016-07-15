package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.CommandException;
import driver.Directory;
import driver.FileSystem;
import driver.Interpreter;
import driver.List;
import driver.Move;

public class MoveTest {

  FileSystem fileSys;

  /**
   * Sets up the FileSystem with two directories fileA and fileB
   */
  @Before
  public void setUp() {
    fileSys = new FileSystem();
    Directory root = fileSys.getRootDirectory();
    Directory A = new Directory("fileA");
    Directory B = new Directory("fileB");
    root.storeFile(A);
    root.storeFile(B);
  }

  /**
   * Cleans up the FileSystem by setting it to null
   */
  @After
  public void cleanUp() {
    fileSys = null;
  }

  /**
   * Testing moveItem when moving one file into another
   * 
   * @throws CommandException The error thrown when an invalid path is given
   */
  @Test
  public void testMoveItemMovingAFile() throws CommandException {
    Move.moveItem(fileSys, "/fileB", "/fileA", true);
    String[] path = Interpreter.filepathToArray("/fileA");
    assertEquals(List.list(fileSys, path), "fileA:\nfileB");

    path = Interpreter.filepathToArray("/");
    assertEquals(List.list(fileSys, path), "fileA");

  }

  /**
   * Testing moveItem when copying a file
   * 
   * @throws CommandException The error thrown when an invalid path is given
   */
  @Test
  public void testMoveItemCopyingAFile() throws CommandException {
    Move.moveItem(fileSys, "/fileB", "/fileA", false);
    String[] path = Interpreter.filepathToArray("/fileA");
    assertEquals(List.list(fileSys, path), "fileA:\nfileB");

    path = Interpreter.filepathToArray("/");
    assertEquals(List.list(fileSys, path), "fileA\nfileB");
  }

  /**
   * Testing moveItem when moving a file into one of its subdirectories
   * 
   * @throws CommandException The error thrown when a file cannot be found
   */
  @Test
  public void testMoveItemMovingIntoSubDirectory() throws CommandException {
    Directory C = new Directory("fileC");
    C.storeFile(new Directory("ChildOfC"));
    try {
      Move.moveItem(fileSys, "/fileC", "/fileC/ChildOfC", true);
    } catch (CommandException e) {

    }
  }

}
