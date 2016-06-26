package test;

import org.junit.*;
import static org.junit.Assert.*;

import driver.PrintWorkingDirectory;
import driver.FileSystem;
import driver.ChangeDirectory;
import driver.CommandException;
import driver.MakeDirectory;

public class PrintWorkingDirectoryTest {

  FileSystem fileSystem;
  String expectedOutput;
  String result;

  @Before
  public void setup() {
    fileSystem = new FileSystem();
  }

  @Test
  public void testPrintRootDirectory() {
    // test printing the root directory
    expectedOutput = "/";
    result = PrintWorkingDirectory.printWD(fileSystem);
    assertEquals(result, expectedOutput);
  }

  @Test
  public void testPrintNewlyMadeDirectory() throws CommandException {
    // test the result of calling PWD on a newly made directory that user
    // cd's into
    expectedOutput = "/dir/";
    MakeDirectory.makeADirectory(fileSystem, "dir");
    ChangeDirectory.changeCurrentDirectory(fileSystem, "dir");
    result = PrintWorkingDirectory.printWD(fileSystem);

    assertEquals(result, expectedOutput);
  }
}
