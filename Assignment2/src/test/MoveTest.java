package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.FileSystem;

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
  
  @Test
  public void testMoveItemMovingOneFile() {
    fail("Not yet implemented");
  }

}
