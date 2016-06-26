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
    // test that nothing is returned when list is called on a directory holding
    // no files
    Directory root = fileSys.getRootDirectory();
    fileSys.setCurrentDirectory(root);
    String output = List.list(fileSys, null);
    assertEquals(output, "");
  }

  @Test
  public void testListWithMultipleDirectories() throws CommandException {
    // test that all the file's contents are returned
    Directory root = fileSys.getRootDirectory();
    root.storeFile(new File("file1"));
    root.storeFile(new File("file2"));
    root.storeFile(new File("file3"));
    fileSys.setCurrentDirectory(root);
    String output = List.list(fileSys, null);
    assertEquals(output, "file1\nfile2\nfile3");
  }

  @Test
  public void testListWithAbsolutePath() throws CommandException {
    // test that all the file's contents are returned at the file found at
    // the given file path
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
    String[] path = Interpreter.commandToArray("/dir1/dir2/dir3");
    String output = List.list(fileSys, path);
    assertEquals(output, "/dir1/dir2/dir3:\nfile1\nfile2");

  }

  @Test
  public void testListException() throws CommandException {
    // test that a CommandException is raised if a directory does not exist
    try {
      List.list(fileSys, Interpreter.filepathToArray("/dir1"));
    } catch (CommandException e) {

    }
  }
}
