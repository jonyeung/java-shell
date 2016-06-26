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
    expectedOutput = "/";
    result = PrintWorkingDirectory.printWD(fileSystem);
    assertEquals(result, expectedOutput);
  }
  
  @Test
  public void testPrintNewlyMadeDirectory() throws CommandException {
    expectedOutput = "/dir/";
    MakeDirectory.makeADirectory(fileSystem, "dir");
    ChangeDirectory.changeCurrentDirectory(fileSystem, "dir");
    result = PrintWorkingDirectory.printWD(fileSystem);
    
    assertEquals(result, expectedOutput);
  }
}
