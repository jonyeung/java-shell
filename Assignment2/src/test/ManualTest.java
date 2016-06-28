package test;

import org.junit.*;
import static org.junit.Assert.*;

import driver.CommandException;
import driver.Manual;

/**
 * Class to test the public methods in Manual.java
 */
public class ManualTest {

  String expectedOutput;
  String result;

  /**
   * Sets the expectedOutput and result strings to empty strings before each
   * test case
   */
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

  /**
   * Tests if the correct output is returned from calling man on the cat command
   * 
   * @throws CommandException
   */
  @Test
  public void testCorrectManualCommand() throws CommandException {

    expectedOutput = "cat FILE1 [FILE2 ...]\n"
        + "\tDisplays the contents of FILE1 and other files"
        + " (i.e. File2 ...) in the shell.";
    result = Manual.printMan("cat");
    assertEquals(result, expectedOutput);
  }

  /**
   * Tests if the exception message is printed when user inputs the wrong
   * command name
   * 
   * @throws CommandException
   */
  @Test
  public void testManualException() throws CommandException {

    try {
      Manual.printMan("invalidcommand");
      fail("invalidcommand does not have a command manual.");
    } catch (CommandException e) {
    }
  }
}
