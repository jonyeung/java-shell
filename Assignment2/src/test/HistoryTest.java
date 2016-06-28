package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.CommandException;
import driver.History;

/**
 * Class for testing the methods in History
 */
public class HistoryTest {

  String expectedOutput;
  String result;
  String[] args = null;

  /**
   * Sets up the history with past commands
   */
  @Before
  public void setup() {
    History.addToHistory("cd ..");
    History.addToHistory("mkdir textFolder");
    History.addToHistory("echo \"Hello World\"");
    History.addToHistory("fsdfasd");
  }

  /**
   * Removes all past history commands
   */
  @After
  public void cleanUp() {
    History.resetHistory();
  }

  /**
   * Test we get proper output when getting history of all past commands
   * 
   * @throws CommandException
   */
  @Test
  public void testAllHistory() throws CommandException {

    History.addToHistory("history");
    expectedOutput = "1. cd ..\n2. mkdir textFolder\n3. echo \"Hello World\"\n"
        + "4. fsdfasd\n5. history";
    result = History.executeHistory(args);
    assertEquals(result, expectedOutput);
  }

  /**
   * Test we get proper output when getting history of all past commands
   * 
   * @throws CommandException
   */
  @Test
  public void testHistory3() throws CommandException {

    History.addToHistory("history 3");
    expectedOutput = "3. echo \"Hello World\"\n4. fsdfasd\n5. history 3";
    args = new String[] {"3"};
    result = History.executeHistory(args);
    assertEquals(result, expectedOutput);
  }

  /**
   * Test an exception is thrown when trying to get more commands than in the
   * history
   * 
   * @throws CommandException
   */
  @Test
  public void testHistoryWithMoreCommandsThatExist() throws CommandException {

    History.addToHistory("history 100");
    try {
      args = new String[] {"100"};
      result = History.executeHistory(args);
      fail("There are not 100 commands in history");
    } catch (CommandException e) {
    }
  }

  /**
   * Test an exception is thrown when given an invalid argument
   * 
   * @throws CommandException
   */
  @Test
  public void testHistoryInvalidParameter() throws CommandException {

    History.addToHistory("history asf");
    try {
      args = new String[] {"asf"};
      result = History.executeHistory(args);
      fail("Wrong type of argument");
    } catch (CommandException e) {
    }
  }


}
