package test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import driver.CommandException;
import driver.Interpreter;

/**
 * This class tests the methods in the Interpreter class
 */
public class InterpreterTest {

  String[] result;
  String[] expected;

  @Test
  public void testCommandToArray() throws CommandException {

    String userInput = "mkdir user1 user2 user3";
    result = Interpreter.commandToArray(userInput);
    expected = new String[] {"mkdir", "user1", "user2", "user3"};
    assertArrayEquals(result, expected);
  }

  @Test
  public void testCommandWithFilepathsToArray() throws CommandException {

    String userInput = "mkdir user1/hi /user1/user2 /user1/user3/";
    result = Interpreter.commandToArray(userInput);
    expected =
        new String[] {"mkdir", "user1/hi", "/user1/user2", "/user1/user3/"};
    assertArrayEquals(result, expected);
  }

  @Test
  public void testCommandWithEchoToArray() throws CommandException {

    String userInput = "echo \"hello this is it\" > outfile";
    result = Interpreter.commandToArray(userInput);
    expected = new String[] {"echo", "hello this is it", ">", "outfile"};
    assertArrayEquals(result, expected);
  }

  @Test
  public void testRelativeFilepathToArray() {

    String filepath = "user1/documents/work/cscb07";
    result = Interpreter.filepathToArray(filepath);
    expected = new String[] {"user1", "documents", "work", "cscb07"};
    assertArrayEquals(result, expected);
  }

  @Test
  public void testAbsoluteFilepathToArray() {

    String filepath = "/user1/documents/work/cscb07";
    result = Interpreter.filepathToArray(filepath);
    expected = new String[] {"user1", "documents", "work", "cscb07"};
    assertArrayEquals(result, expected);
  }

  @Test
  public void testMkdirValidInput() throws CommandException {

    String userInput = "       mkdir    user1 user2      user3   ";
    assertTrue(Interpreter.validInput(userInput));
  }

  @Test
  public void testMkdirFailValidInput() throws CommandException {

    try {
      String userInput = "mkdir";
      Interpreter.validInput(userInput);
      fail("Not enough arguments are given");
    } catch (CommandException e) {
    }
  }

  @Test
  public void testCdValidInput() {

    // String userInput = " mkdir user1 user2 user3 ";
    // assertTrue(Interpreter.validInput(userInput));
  }

  @Test
  public void testCdFailValidInput() {

    // String userInput = "mkdir";
    // assertFalse(Interpreter.validInput(userInput));
  }

}
