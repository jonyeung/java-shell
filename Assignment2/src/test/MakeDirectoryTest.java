package test;

import org.junit.*;
import static org.junit.Assert.*;

import driver.FileSystem;
import driver.CommandException;
import driver.MakeDirectory;
import driver.Directory;

public class MakeDirectoryTest {

  @Test
  public void setup() throws CommandException {

    Directory directory = new Directory("root");
    String expectedDirectory = directory.getName();

    FileSystem fileSystem = new FileSystem();
    MakeDirectory.makeADirectory(fileSystem, "user1");
    String actualDirectory = fileSystem.getCurrentDirectory().getName();

    assertEquals(expectedDirectory, actualDirectory);
  }
}
