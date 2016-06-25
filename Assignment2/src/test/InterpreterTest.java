package test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import driver.CommandException;
import driver.Interpreter;

public class InterpreterTest {


  @Test
  public void testCommandToArray() throws CommandException {

    String userInput = "mkdir user1 user2 user3";
    String[] result = Interpreter.commandToArray(userInput);
    String[] expected = {"mkdir", "user1", "user2", "user3"};
    assertArrayEquals(result, expected);
  }

  @Test
  public void testCommandWithFilepathsToArray() throws CommandException {

    String userInput = "mkdir user1/hi /user1/user2 /user1/user3/";
    String[] result = Interpreter.commandToArray(userInput);
    String[] expected = {"mkdir", "user1/hi", "/user1/user2", "/user1/user3/"};
    assertArrayEquals(result, expected);
  }
  
  @Test
  public void testCommandWithEchoToArray() throws CommandException {

    String userInput = "echo \"hello this is it\" > outfile";
    String[] result = Interpreter.commandToArray(userInput);
    String[] expected = {"echo", "hello this is it", ">", "outfile"};
    assertArrayEquals(result, expected);
  }

  @Test
  public void testRelativeFilepathToArray() {
    
    String filepath = "user1/documents/work/cscb07";
    String[] result = Interpreter.filepathToArray(filepath);
    String[] expected = {"user1", "documents", "work", "cscb07"};
    assertArrayEquals(result, expected);
  }

  @Test
  public void testAbsoluteFilepathToArray() {
    
    String filepath = "/user1/documents/work/cscb07";
    String[] result = Interpreter.filepathToArray(filepath);
    String[] expected = {"user1", "documents", "work", "cscb07"};
    assertArrayEquals(result, expected);
  }

  @Test
  public void testMkdirValidInput() throws CommandException {
    
    String userInput = "       mkdir    user1 user2      user3   ";
    assertTrue(Interpreter.validInput(userInput));
  }
  
  @Test
  public void testMkdirFailValidInput() throws CommandException {

    String userInput = "mkdir";
    assertFalse(Interpreter.validInput(userInput));
  }
  
  @Test
  public void testCdValidInput() {
    
    //String userInput = "       mkdir    user1 user2      user3   ";
    //assertTrue(Interpreter.validInput(userInput));
  }
  
  @Test
  public void testCdFailValidInput() {

    //String userInput = "mkdir";
    //assertFalse(Interpreter.validInput(userInput));
  }

}
