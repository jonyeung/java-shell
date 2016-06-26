package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.CommandException;
import driver.Directory;
import driver.File;
import driver.FileSystem;
import driver.Interpreter;
import driver.List;

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
  public void testListWithNoDirectories() throws CommandException {
    Directory root = new Directory("root");
    fileSys.setCurrentDirectory(root);
    String output = List.list(fileSys, null);
    assertEquals(output, "");
  }
  
  @Test
  public void testListWithMultipleDirectories() throws CommandException {
    Directory root = new Directory("root");
    root.storeFile(new File("file1"));
    root.storeFile(new File("file2"));
    root.storeFile(new File("file3"));
    fileSys.setCurrentDirectory(root);
    String output = List.list(fileSys, null);
    assertEquals(output, "file1\nfile2\nfile3");
  }
  
  @Test
  public void testListWithAbsolutePath() throws CommandException {
    Directory root = fileSys.getRootDirectory();
    Directory dir1 = new Directory("dir1");
    Directory dir2 = new Directory("dir2");
    Directory dir3 = new Directory("dir3");
    Directory file1 = new Directory("file1");
    Directory file2 = new Directory("file2");
    root.storeFile(dir1);
    dir1.storeFile(dir2);
    dir2.storeFile(dir3);
    dir3.storeFile(file1);
    dir3.storeFile(file2);
    String output = List.list(fileSys, Interpreter.filepathToArray("/root" +
    		"/dir1/dir2"));
    assertEquals(output, "file1\nfile2");
    
  }
}
