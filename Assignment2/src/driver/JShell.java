// **********************************************************
// Assignment2:
// Student1:Jonathan Yeung
// UTORID user_name:yeungj21
// UT Student #:1002090532
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
import java.io.IOException;

import driver.Directory;
import driver.File;
import driver.Interpreter;
import driver.TextFile;
import driver.Tree;
import driver.Node;

public class JShell {

  public static void main(String[] args) throws IOException {

    // User input and default start of line
    String userInput;
    String startOfLine = "/#: ";

    // Boolean to check whether user has quit
    Boolean exitStatus = false;

    // Interpreter
    // TODO Requires discussion with group to determine how to
    // utilize the interpreter

    // Create a new buffered reader to fetch input from console
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // Continually prompt user for input
    do {

      // Print the start of each line before input
      System.out.print(startOfLine);

      // Retrieve input from user
      userInput = br.readLine();
      History.addToHistory(userInput);

      // Terminate program if user types "exit"
      if (userInput.equals("exit")) {
        // Terminate loop
        exitStatus = true;
      } else {
        interpretInput(userInput);
        // Check for valid input
        // If user inputs invalid command name, raise commandNameException
        // If user inputs invalid argument for the command name, raise
        // commandArgException
        // Otherwise, execute the command, as wanted
      }

    } while (exitStatus == false);
  }

  /**
   * Interprets the user input
   * 
   * @param userInput The line of input entered by the user to be interpreted
   */
  public static void interpretInput(String userInput) {

    // Check if the input is valid
    if (Interpreter.validInput(userInput) == true) {

      // Break up the user input
      String[] inputArray = Interpreter.commandToArray(userInput);
      // inputArray[0] is the command name
      String commandName = inputArray[0];
      // # of arguments in the input
      String[] commandArgs = null;

      // Copy the arguments from inputArray to inputArguments, if there are any
      if (inputArray.length > 1) {

        commandArgs = new String[inputArray.length - 1];
        // Loop through the length of the inputArray
        for (int i = 0; i < inputArray.length - 1; i++) {
          // Copy arguments from inputArray to inputArguments
          commandArgs[i] = inputArray[i + 1];
        }
      }
      // execute the command, convert command name to lowercase
      executeCommand(commandName.toLowerCase(), commandArgs);
    } else {
      System.out.println("Invalid input");
      // Throw exceptions
      // Exception 1: Invalid command name
      // Exception 2: Invalid argument(s)
    }
  }

  /**
   * Executes the command given the command name and its arguments, where both
   * the command name and its arguments are valid
   * 
   * @param commandName The command name of the command to be executed
   * @param commandArgs The argument(s) for the command to be executed
   */
  public static void executeCommand(String commandName, String[] commandArgs) {
    // TODO:
    // 1. Need filesystem to finish mkdir completely
    if (commandName.equals("mkdir")) {
      String relativeIndicator = "/";
      for (int i = 1; i < commandArgs.length; i++) {
        if (commandArgs[i].equals(relativeIndicator)) {
          // TODO MakeDirectory.mkdirRelative(fileSystem, commandArgs[i]);
        } else {
          // TODO MakeDirectory.mkdirAbsolute(fileSystem. commandArgs[i]);
        }
      }
    } else if (commandName.equals("cd")) {
      // TODO:
      // 1. Need filesystem to finish cd completely
      // ChangeDirectory.cd(fileSystem, commandArgs[0]);
    } else if (commandName.equals("ls")) {
      // TODO:
      // 1. Need filesystem to finish cd completely
      if (commandArgs != null) {
        for (int i = 0; i < commandArgs.length; i++) {
          List.printDirectoryContentsGivenPath(commandArgs[i]);
        }
      } else {
        // TODO List.listContents(fileSystem);
      }
    } else if (commandName.equals("pwd")) {
      // PrintWorkingDirectory.printWD(fileSystem.getDir());
    } else if (commandName.equals("pushd")) {
      // pushd(inputArguments)
    } else if (commandName.equals("popd")) {
      // popd()
    } else if (commandName.equals("history")) {
      // Call the history command on the first argument
      if (commandArgs != null) {
        History.printHistory(Integer.parseInt(commandArgs[0]));
      } else {
        History.printAllHistory();
      }
    } else if (commandName.equals("cat")) {
      // cat(inputArguments)
    } else if (commandName.equals("echo")) {
      // echo(inputArguments)
    } else if (commandName.equals("man")) {
      // Call the man command on the first argument
      Man.printMan(commandArgs[0]);
    }
  }
}
