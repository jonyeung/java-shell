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
  String[] args = {};

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
   * @throws CommandException If user tries to access invalid elements in
   *         history
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
   * @throws CommandException If user tries to access invalid elements in
   *         history
   */
  @Test
  public void testHistoryThree() throws CommandException {

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
   * @throws CommandException If user tries to access invalid elements in
   *         history
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
   * @throws CommandException If user inputs invalid arguments
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

  /**
   * Test recalling an exact command via !number
   * 
   * @throws CommandException If invalid number of history elements requested
   */
  @Test
  public void testHistoryRecallExactCommand() throws CommandException {
    expectedOutput = "mkdir textFolder";
    result = History.recallExactCommand("2");
    assertEquals(result, expectedOutput);
  }

  /**
   * Test printing entire history after calling !number command, which should
   * not be added to the history
   * 
   * @throws CommandException If invalid number of history elements requested
   */
  @Test
  public void testPrintHistoryAfterRecallExactCommand()
      throws CommandException {
    History.recallExactCommand("1");
    args = new String[] {"4"};
    result = History.executeHistory(args);
    expectedOutput = "1. cd ..\n2. mkdir textFolder\n3. echo \"Hello World\"\n"
        + "4. fsdfasd";
    assertEquals(result, expectedOutput);
  }

  /**
   * Test recalling a command after 10th digit.
   * 
   * @throws CommandException For invalid arguments in executeHistory
   */
  public void testRecallDoubleDigitCommand() throws CommandException {
    History.addToHistory("history 5");
    History.addToHistory("history 6");
    History.addToHistory("history 7");
    History.addToHistory("history 8");
    History.addToHistory("history 9");
    History.addToHistory("history 10");
    History.addToHistory("history 11");
    args = new String[] {"11"};
    result = History.executeHistory(args);
    expectedOutput = "history 11";
    assertEquals(result, expectedOutput);
  }

  /**
   * Testing recalling exact command with invalid number.
   * 
   * @throws CommandException For invalid arguments in recallExactComand
   */
  @Test
  public void testRecallExactCommandInvalidNumber() throws CommandException {
    try {
      result = History.recallExactCommand("5");
      fail("There is no 5th command in history.");
    } catch (CommandException e) {
    }
  }

  /**
   * Testing recalling exact command with non-numeric input
   * 
   * @throws CommandException For invalid arguments in recallExactComand
   */
  @Test
  public void testRecallExactCommandInvalidInput() throws CommandException {
    try {
      result = History.recallExactCommand("abcd");
      fail("Cannot recall commands using non-numeric input.");
    } catch (CommandException e) {
    }
  }

  /**
   * Testing recalling exact command with number less than 1
   * 
   * @throws CommandException For invalid arguments in recallExactComand
   */
  @Test
  public void testRecallExactCommandNumberLessThanOne()
      throws CommandException {
    try {
      result = History.recallExactCommand("-1");
      fail("Cannot recall commands before first command in history.");
    } catch (CommandException e) {
    }
  }
}
