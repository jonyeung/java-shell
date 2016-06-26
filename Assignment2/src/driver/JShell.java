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
import java.util.Arrays;
import java.io.IOException;

import driver.Interpreter;
import driver.FileSystem;

public class JShell {

  public static FileSystem fileSystem;

  public static void main(String[] args) throws IOException, CommandException {

    // User input and default start of line
    String userInput;
    String startOfLine = "/#: ";

    // File system
    fileSystem = new FileSystem();

    // Boolean to check whether user has quit
    Boolean exitStatus = false;

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
        // Interpret the input
        interpretInput(userInput);
      }

    } while (exitStatus == false);
  }

  /**
   * Interprets the user input and runs the command if it is valid
   * 
   * @param userInput The line of input entered by the user to be interpreted
   * @throws CommandException
   */
  public static void interpretInput(String userInput) throws CommandException {
    try {
      // Execute the command accordingly if it is valid
      if (Interpreter.validInput(userInput) == true) {

        // Break up the user input
        String[] inputArray = Interpreter.commandToArray(userInput);
        // inputArray[0] is the command name
        String commandName = inputArray[0];
        // # of arguments in the input
        String[] commandArgs = null;

        // Copy the arguments from inputArray to inputArguments, if
        // there are any
        if (inputArray.length > 1) {
          commandArgs = Arrays.copyOfRange(inputArray, 1, inputArray.length);
        }

        // Execute the command, convert command name to lowercase
        executeCommand(commandName.toLowerCase(), commandArgs);
      }
    } catch (CommandException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Executes the command given the command name and its arguments, where both
   * the command name and its arguments are valid
   * 
   * @param commandName The command name of the command to be executed
   * @param commandArgs The argument(s) for the command to be executed
   * @throws CommandException
   */
  public static void executeCommand(String commandName, String[] commandArgs)
      throws CommandException {
    // Mkdir
    if (commandName.equals("mkdir")) {
      for (int i = 0; i < commandArgs.length; i++) {
        MakeDirectory.makeADirectory(fileSystem, commandArgs[i]);
      }
    } else if (commandName.equals("cd")) {
      ChangeDirectory.changeCurrentDirectory(fileSystem, commandArgs[0]);
    } else if (commandName.equals("ls")) {
      List.list(fileSystem, commandArgs);
    } else if (commandName.equals("pwd")) {
      System.out.println(PrintWorkingDirectory.printWD(fileSystem));
    } else if (commandName.equals("pushd")) {
      DirectoryStack.pushd(fileSystem, commandArgs[0]);
    } else if (commandName.equals("popd")) {
      DirectoryStack.popd(fileSystem);
    } else if (commandName.equals("history")) {
      // Call the history command on the first argument
      String output;
      if (commandArgs != null) {
        output = History.printHistory(Integer.parseInt(commandArgs[0]));
      } else {
        output = History.printAllHistory();
      }
      System.out.println(output);
    } else if (commandName.equals("cat")) {
      // cat(inputArguments)
    } else if (commandName.equals("echo")) {
      // echo(inputArguments)
    } else if (commandName.equals("man")) {
      // Call the man command on the first argument
      Manual.printMan(commandArgs[0]);
    }
  }

}
