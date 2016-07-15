// **********************************************************
// Assignment2:
// Student1: Jonathan Yeung
// UTORID user_name: yeungj21
// UT Student #: 1002090532
// Author:
//
// Student2: Boni Xie Zhang
// UTORID user_name: xiezhang
// UT Student #: 1001391727
// Author:
//
// Student3: Jason Chan
// UTORID user_name: chanja49
// UT Student #: 1002550367
// Author:
//
// Student4: Gurpreet Gill
// UTORID user_name: gillgu19
// UT Student #: 1002211313
// Author:
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;

import driver.Interpreter;
import driver.FileSystem;

/**
 * This class runs the main method of the program, allowing users to interact
 * with it by typing in commands.
 */
public class JShell {

  private static Boolean exitStatus;
  public static FileSystem fileSystem;
  public static boolean addedToHistory;

  /**
   * Main method of the program. Continues to prompt users for input and
   * terminates upon user request.
   * 
   * @param args The arguments inputed by the user in the program
   * @throws IOException The exception thrown for the bufferedreader for input
   *         and output queries
   * @throws CommandException The exception if command cannot run
   */
  public static void main(String[] args) throws IOException, CommandException {

    // User input and default start of line
    String userInput;
    String startOfLine = "/#: ";

    // File system
    fileSystem = new FileSystem();

    // Set up the interpreter hash table
    Interpreter.setUp();

    // Boolean to check whether user has quit
    exitStatus = false;

    // Create a new buffered reader to fetch input from console
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // Continually prompt user for input
    do {

      // Print the start of each line before input
      System.out.print(startOfLine);

      // Retrieve input from user
      userInput = (br.readLine());
      addedToHistory = false;

      // Interpret the input
      interpretInput(userInput);

    } while (exitStatus == false);
  }

  /**
   * Interprets the user input and runs the command if it is valid
   * 
   * @param userInput The line of input entered by the user to be interpreted
   * @throws CommandException The error returned if the command is invalid
   */
  private static void interpretInput(String userInput) throws CommandException {

    try {
      if (!userInput.startsWith("!") & !userInput.startsWith("man")) {
        History.addToHistory(userInput);
        addedToHistory = true;
      }
      // Break up the user input
      String[] inputArray = Interpreter.commandToArray(userInput);
      // inputArray[0] is the command name
      String commandName = inputArray[0];
      // # of arguments in the input
      String[] commandArgs = {};

      // Copy the arguments from inputArray to inputArguments, if
      // there are any
      if (inputArray.length > 1) {
        commandArgs = Arrays.copyOfRange(inputArray, 1, inputArray.length);
      }

      // Execute the command accordingly if it is valid
      if (Interpreter.validInput(userInput) == true) {
        // Execute the command
        executeCommand(commandName, commandArgs);
      }
    } catch (CommandException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
    }
  }

  /**
   * Executes the command given the command name and its arguments, where both
   * the command name and its arguments are valid
   * 
   * @param commandName The command name of the command to be executed
   * @param commandArgs The argument(s) for the command to be executed
   * @throws CommandException The error thrown if command cannot be executed
   */
  private static void executeCommand(String commandName, String[] commandArgs)
      throws CommandException {

    String output = "";

    boolean outputToFile = false;
    String[] redirectArgs = null;
    int argLen = commandArgs.length;

    // Redirection handling
    if (argLen >= 2 && (commandArgs[argLen - 2].equals(">")
        || commandArgs[argLen - 2].equals(">>"))) {
      redirectArgs = Arrays.copyOfRange(commandArgs, argLen - 2, argLen);
      commandArgs = Arrays.copyOfRange(commandArgs, 0, argLen - 2);
      outputToFile = true;
    }

    // Execute the appropriate command
    switch (commandName) {

      // Assertion error for extra safety
      default:
        throw new AssertionError(commandName);

        // Cases below
      case "mkdir":
        // Create a directory for each command argument
        for (String command : commandArgs) {
          MakeDirectory.makeADirectory(fileSystem, command);
        }
        break;

      case "cd":
        // Change directory to the first command arg
        ChangeDirectory.changeCurrentDirectory(fileSystem, commandArgs[0]);
        break;

      case "ls":
        // Add the list to output
        output = List.list(fileSystem, commandArgs);
        break;

      case "pwd":
        // Add the working directory to output
        output = PrintWorkingDirectory.printWD(fileSystem);
        break;

      case "pushd":
        // Push the first command arg to directory stack
        DirectoryStack.pushd(fileSystem, commandArgs[0]);
        break;

      case "popd":
        // Pop the last item on the directory stack
        DirectoryStack.popd(fileSystem);
        break;

      case "history":
        // Add the history to output
        output = History.executeHistory(commandArgs);
        break;

      case "cat":
        // Add the file contents of the each command to output
        output = Cat.cat(fileSystem, commandArgs);
        break;

      case "echo":
        // Add the contents of the command args to output
        output = Echo.executeEcho(fileSystem, commandArgs);
        break;

      case "man":
        // Add the manual for the command to output
        output = Manual.printMan(executeMan(commandName, commandArgs));
        break;

      case "!":
        // Recall the last command at position commandArgs[0]
        interpretInput(History.recallExactCommand(commandArgs[0]));
        break;

      case "mv":
        // Move the item from oldpath to new path
        Move.moveItem(fileSystem, commandArgs[0], commandArgs[1], true);
        break;

      case "cp":
        // Copy the item from oldpath to new path
        Move.moveItem(fileSystem, commandArgs[0], commandArgs[1], false);
        break;

      case "exit":
        // Break out of main while loop
        exitStatus = true;
        break;

      case "grep":
        // Execute grep on command args
        output = MatchRegex.executeGrep(fileSystem, commandArgs);
        break;
    }

    if (!output.equals("")) {
      // If the user wants to redirect output to a file, then call echo on file
      if (outputToFile) {
        output = "\"" + output + "\"";
        String[] echoCommandArgs = {output, redirectArgs[0], redirectArgs[1]};
        Echo.executeEcho(fileSystem, echoCommandArgs);
      } else {
        System.out.println(output);
      }
    }
  }

  /**
   * Helper function to execute Man, taking into account !number in command
   * arguments
   * 
   * @param commandName Name of the command
   * @param commandArgs Arguments for the command
   * @return String The correct manual page to print
   * @throws CommandException If invalid arguments given.
   */
  private static String executeMan(String commandName, String[] commandArgs)
      throws CommandException {
    // Name of command
    String manToPrint = commandArgs[0];
    String historyToAdd = commandName + " ";
    // Take into account calling man on arguments with "!number" in them
    if (commandArgs[0].startsWith("!") && commandArgs[0].length() > 1) {
      commandArgs[0] = History.recallExactCommand(commandArgs[0].substring(1));
      manToPrint = commandArgs[0].split(" ")[0];
    }
    // Add all retrieved commands to the new history to add
    for (int i = 0; i < commandArgs.length; i++) {
      historyToAdd += commandArgs[i];
    }
    // Add the history
    if (addedToHistory == false) {
      History.addToHistory(historyToAdd);
    }
    return manToPrint;
  }

}
