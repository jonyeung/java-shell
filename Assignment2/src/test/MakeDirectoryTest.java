package test;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import driver.FileSystem;
import driver.CommandException;
import driver.MakeDirectory;
import driver.File;

public class MakeDirectoryTest {

  @Test
  public void testMakeDirectoryUser1() throws CommandException {

    FileSystem fileSystem = new FileSystem();
    MakeDirectory.makeADirectory(fileSystem, "user1");

    ArrayList<File> expected = new ArrayList<File>();
    ArrayList<File> actual = fileSystem.getCurrentDirectory().getStoredFiles();
    assertEquals(expected,actual);
  }
}
