package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import driver.CommandException;
import driver.Directory;
import driver.FileSystem;
import driver.Interpreter;

/**
 * Class for testing the methods in Interpreter
 */
public class InterpreterTest {

  String[] result;
  String[] expected;

  /**
   * Sets up the fileSystem and expected/actual result strings
   * 
   * @throws Exception
   */
  @Before
  public void setUp() {

    Interpreter.setUp();
  }

  /**
   * Test that a valid input will work
   * 
   * @throws CommandException
   */
  @Test
  public void testCommandToArray() throws CommandException {

    String userInput = "mkdir user1 user2 user3";
    result = Interpreter.commandToArray(userInput);
    expected = new String[] {"mkdir", "user1", "user2", "user3"};
    assertArrayEquals(result, expected);
  }

  /**
   * Test that input with multiple file paths is valid
   * 
   * @throws CommandException
   */
  @Test
  public void testCommandWithFilepathsToArray() throws CommandException {

    String userInput = "ls user1/hi /user1/user2 /user1/user3/";
    result = Interpreter.commandToArray(userInput);
    expected = new String[] {"ls", "user1/hi", "/user1/user2", "/user1/user3/"};
    assertArrayEquals(result, expected);
  }

  /**
   * Test that a command with echo will keep anything in double quotes together
   * 
   * @throws CommandException
   */
  @Test
  public void testCommandWithEchoToArray() throws CommandException {

    String userInput = "echo \"hello this is it\" > outfile";
    result = Interpreter.commandToArray(userInput);
    expected = new String[] {"echo", "\"hello this is it\"", ">", "outfile"};
    assertArrayEquals(result, expected);
  }

  /**
   * Test that converting a relative file path to an array of strings works
   * 
   * @throws CommandException
   * 
   */
  @Test
  public void testRelativeFilepathToArray() throws CommandException {

    String filepath = "user1/documents/work/cscb07";
    result = Interpreter.filepathToArray(filepath);
    expected = new String[] {"user1", "documents", "work", "cscb07"};
    assertArrayEquals(result, expected);
  }

  /**
   * Test that converting an absolute file path to an array of strings works
   * 
   * @throws CommandException
   * 
   */
  @Test
  public void testAbsoluteFilepathToArray() throws CommandException {

    String filepath = "/user1/documents/work/cscb07";
    result = Interpreter.filepathToArray(filepath);
    expected = new String[] {"user1", "documents", "work", "cscb07"};
    assertArrayEquals(result, expected);
  }

  /**
   * Test that the input is valid even if it has bad space formatting
   * 
   * @throws CommandException
   */
  @Test
  public void testValidInputWithSpacesInInput() throws CommandException {

    String userInput = "       mkdir    user1 user2      user3   ";
    assertTrue(Interpreter.validInput(userInput));
  }

  /**
   * Test that the input fails if not enough arguments are given
   * 
   * @throws CommandException
   */
  @Test
  public void testFailNotEnoughArgs() throws CommandException {

    try {
      String userInput = "pushd";
      Interpreter.validInput(userInput);
      fail("Not enough arguments are given");
    } catch (CommandException e) {
    }
  }

  /**
   * Test that the input fails if the command is not real
   * 
   * @throws CommandException
   */
  @Test
  public void testFakeCommand() throws CommandException {

    try {
      String userInput = " hi !";
      Interpreter.validInput(userInput);
      fail("Invalid command");
    } catch (CommandException e) {
    }
  }

  /**
   * Test that the new file name given is valid
   * 
   * @throws CommandException
   */
  @Test
  public void testCheckValidFileName() throws CommandException {

    String userInput = "Documents";
    FileSystem fileSystem = new FileSystem();
    Directory root = fileSystem.getRootDirectory();
    assertTrue(Interpreter.checkFileName(userInput, root));
  }

  /**
   * Test that the new file name given is valid
   * 
   * @throws CommandException
   */
  @Test
  public void testCheckInvalidFileName() throws CommandException {

    FileSystem fileSystem = new FileSystem();
    Directory root = fileSystem.getRootDirectory();
    String userInput = "!@";
    assertFalse(Interpreter.checkFileName(userInput, root));

    userInput = ".";
    assertFalse(Interpreter.checkFileName(userInput, root));
  }
}
