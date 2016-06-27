package test;

import org.junit.*;
import static org.junit.Assert.*;

import driver.CommandException;
import driver.Manual;

public class ManualTest {

  String expectedOutput;
  String result;

  @Before
  public void setUp() {
    result = "";
    expectedOutput = "";
  }

  @After
  public void cleanUp() {
    result = null;
    expectedOutput = null;
  }

  @Test
  public void testCorrectManualCommand() throws CommandException {
    // tests if the correct output is returned from calling man on the cat
    // command
    expectedOutput =
        "cat FILE1 [FILE2 ...]\n"
            + "\tDisplays the contents of FILE1 and other files"
            + " (i.e. File2 ...) in the shell.";
    result = Manual.printMan("cat");
    assertEquals(result, expectedOutput);
  }

  @Test
  public void testManualException() throws CommandException {
    // tests if the exception message is printed when user inputs the wrong
    // command name
    try {
      Manual.printMan("invalidcommand");
      fail("invalidcommand does not have a command manual.");
    } catch (CommandException e) {
    }

  }
}
