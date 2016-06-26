package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.CommandException;
import driver.History;

public class HistoryTest {

  String expectedOutput;
  String result;

  @Before
  public void setup() {
    History.addToHistory("cd ..");
    History.addToHistory("mkdir textFolder");
    History.addToHistory("echo \"Hello World\"");
    History.addToHistory("fsdfasd");
  }

  @After
  public void cleanUp() {
    History.resetHistory();
  }

  @Test
  public void testAllHistory() throws CommandException {
    // test we get proper output when getting history of all past commands
    History.addToHistory("history");
    expectedOutput = "1. cd ..\n2. mkdir textFolder\n3. echo \"Hello World\"\n"
        + "4. fsdfasd\n5. history";
    result = History.printAllHistory();
    assertEquals(result, expectedOutput);
  }

  @Test
  public void testHistory3() throws CommandException {
    // test we get proper output when getting history of all past commands
    History.addToHistory("history 3");
    expectedOutput = "3. echo \"Hello World\"\n4. fsdfasd\n5. history 3";
    result = History.printHistory(3);
    assertEquals(result, expectedOutput);
  }

  @Test
  public void testHistoryException() throws CommandException {
    // test an exception is thrown when trying to get more commands than in the
    // history
    History.addToHistory("history 100");
    try {
      result = History.printHistory(100);
      fail("There are not 100 commands in history");
    } catch (CommandException e) {
    }
  }


}
