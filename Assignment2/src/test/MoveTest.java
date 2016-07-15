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
    root.storeFile(new Directory("fileA"));
    root.storeFile(new Directory("fileB"));
  }
  
  /**
   * Cleans up the FileSystem by setting it to null
   */
  @After
  public void cleanUp() {
    fileSys = null;
  }
  
  /**
   * Testing moveItem when moving one directory into another
   * @throws CommandException 
   */
  @Test
  public void testMoveItemForDirectory() throws CommandException {
    Move.moveItem(fileSys, "/fileB", "/fileA", true);
    String[] path = Interpreter.filepathToArray("/fileA");
    assertEquals(List.list(fileSys, path), "fileA:\nfileB");
    
    path = Interpreter.filepathToArray("/");
    assertEquals(List.list(fileSys, path), "fileA");
    
  }

}
