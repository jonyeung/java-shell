package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.File;
import driver.FileSystem;

public class ListTest {

  FileSystem fileSys;
  
  @Before
  public void setUp() {
    fileSys = new FileSystem();
  }
  
  @After
  public void cleanUp() {
    fileSys = null;
  }
  
  @Test
  public void testEmptyListContents() {
    Directory root = new Directory("root");
    root.storeFile(new File("file1"));
    root.storeFile(new File("file2"));
    root.storeFile(new File("file3"));
    fileSys.setCurrentDirectory(root);
  }

}
