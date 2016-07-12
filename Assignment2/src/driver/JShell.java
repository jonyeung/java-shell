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
  private static int infLoop = 0;

  public static FileSystem fileSystem;

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
      // Break up the user input
      String[] inputArray = Interpreter.commandToArray(userInput);
      // inputArray[0] is the command name
      String commandName = inputArray[0];
      // # of arguments in the input
      String[] commandArgs = {};
      // The full command string
      String fullCommand = commandName;

      // Copy the arguments from inputArray to inputArguments, if
      // there are any
      if (inputArray.length > 1) {
        commandArgs = Arrays.copyOfRange(inputArray, 1, inputArray.length);
      }

      // Check for "!" arguments. If they exist, replace the argument
      // with the command to be return by the argument
      for (int i = 0; i < commandArgs.length; i++) {
        if (commandArgs[i].startsWith("!")) {
          if (commandName.equals("man")) {
            if (commandArgs[i].length() > 1) {
              // Get the command to be executed
              commandArgs[i] =
                  History.recallExactCommand(commandArgs[i].substring(1));
              System.out.println(commandArgs[i]);
            }
          }
          // Add the command return to the fullCommand string.

        }
        fullCommand += " " + commandArgs[i];
      }
      // Add the full command to history
      History.addToHistory(fullCommand);

      // Execute the command accordingly if it is valid
      if (Interpreter.validInput(userInput) == true) {
        // Execute the command
        executeCommand(commandName, commandArgs);
      }
    } catch (

    CommandException e) {
      System.out.println(e.getMessage());
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
    String infLoopMessage = "Infinite loop. Please input a valid number.";
    int argLen = commandArgs.length;

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
        System.out.println(commandArgs[0]);
        // Add the manual for the command to output
        String[] commands = commandArgs[0].split(" ");
        output = Manual.printMan(commands[0]);
        break;

      case "!":
        try {
          interpretInput(History.recallExactCommand(commandArgs[0]));
          History.addToHistory(History.recallExactCommand(commandArgs[0]));
        } catch (StackOverflowError e) {
          if (!output.equals(infLoopMessage)) {
            output += infLoopMessage;
          }
        }
        break;

      case "mv":
        Move.moveItem(fileSystem, commandArgs[0], commandArgs[1], true);
        break;

      case "cp":
        Move.moveItem(fileSystem, commandArgs[0], commandArgs[1], false);
        break;

      case "exit":
        exitStatus = true;
        break;

      case "grep":
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
}
