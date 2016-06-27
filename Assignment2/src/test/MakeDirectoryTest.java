package test;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import driver.FileSystem;
import driver.CommandException;
import driver.MakeDirectory;
import driver.Directory;
import driver.File;

public class MakeDirectoryTest {

  Directory directory;

  @Before
  public void setUp() {
    directory = new Directory("root");
  }

  @After
  public void cleanUp() {
    directory = null;
  }

  @Test
  public void testNoDirectoriesExceptRoot() throws CommandException {

    String expectedDirectory = directory.getName();

    FileSystem fileSystem = new FileSystem();
    String actualDirectory = fileSystem.getCurrentDirectory().getName();

    assertEquals(expectedDirectory, actualDirectory);
  }

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
