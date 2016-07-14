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
   */
  @Before
  public void setUp() {

    Interpreter.setUp();
  }

  /**
   * Test that a valid input will work
   * 
   * @throws CommandException When an odd number of " characters are given
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
   * @throws CommandException When an odd number of " characters are given
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
   * @throws CommandException When an odd number of " characters are given
   */
  @Test
  public void testCommandWithEchoToArray() throws CommandException {

    String userInput = "echo \"hello this is it\" > outfile";
    result = Interpreter.commandToArray(userInput);
    expected = new String[] {"echo", "\"hello this is it\"", ">", "outfile"};
    assertArrayEquals(result, expected);
  }
  
  /**
   * Test that a command with ! will separate the command from the arugment
   * 
   * @throws CommandException When an odd number of " characters are given
   */
  @Test
  public void testCommandWithExclamationMark() throws CommandException {

    String userInput = "!103";
    result = Interpreter.commandToArray(userInput);
    expected = new String[] {"!", "103"};
    assertArrayEquals(result, expected);
  }
  
  /**
   * Test that the input fails if a single " is used
   */
  @Test
  public void testCommandSingleDoubleQuote() {

    try {
      String userInput = "echo \"hi > out";
      Interpreter.validInput(userInput);
      fail("Only a single quote is used");
    } catch (CommandException e) {
    }
    
    try {
      String userInput = "echo hi\" > out";
      Interpreter.validInput(userInput);
      fail("Only a single quote is used");
    } catch (CommandException e) {
    }
  }

  /**
   * Test that converting a relative file path to an array of strings works
   * 
   * @throws CommandException When an odd number of " characters are given
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
   * @throws CommandException When an odd number of " characters are given
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
   * @throws CommandException When no input is given, Command name is not valid,
   *         The wrong number of arguments are given, Improper use of chevrons
   */
  @Test
  public void testValidInputWithSpacesInInput() throws CommandException {

    String userInput = "       mkdir    user1 user2      user3   ";
    assertTrue(Interpreter.validInput(userInput));
  }
  
  /**
   * Test that the input fails if nothing is given as input
   */
  @Test
  public void testFailNoCommand() {

    try {
      String userInput = "";
      Interpreter.validInput(userInput);
      fail("No command given");
    } catch (CommandException e) {
    }
  }

  /**
   * Test that the input fails if not enough arguments are given
   */
  @Test
  public void testFailNotEnoughArgs() {

    try {
      String userInput = "pushd";
      Interpreter.validInput(userInput);
      fail("Not enough arguments are given");
    } catch (CommandException e) {
    }
  }

  /**
   * Test that the input fails if the command is not real
   */
  @Test
  public void testFakeCommand() {

    try {
      String userInput = " hi !";
      Interpreter.validInput(userInput);
      fail("Invalid command");
    } catch (CommandException e) {
    }
  }

  /**
   * Test improper chevron use raises exceptions
   */
  @Test
  public void testImproperChevrons() {

    try {
      String userInput = "echo > > out";
      Interpreter.validInput(userInput);
      fail("Invalid chevron use");
    } catch (CommandException e) {
    }
    
    try {
      String userInput = "ls >> >";
      Interpreter.validInput(userInput);
      fail("Invalid chevron use");
    } catch (CommandException e) {
    }
  }
  
  /**
   * Test that the new file name given is valid
   * 
   * @throws CommandException When an odd number of " characters are in fileName
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
   * @throws CommandException When an odd number of " characters are in fileName
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
