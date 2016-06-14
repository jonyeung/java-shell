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

    // Interpreter
    // TODO Requires discussion with group to determine how to
    // utilize the interpreter

    // Create a new buffered reader to fetch input from console
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // Continually prompt user for input
    while (true) {
      // Print the start of each line before input
      System.out.print(startOfLine);
      // Retrieve input from user
      userInput = br.readLine();

      // Terminate program if user types "exit"
      if (userInput.equals("exit")) {
        // Terminate loop
        break;
      } else {
        // Check for valid input
        // If user inputs invalid command name, raise commandNameException
        // If user inputs invalid argument for the command name, raise
        // commandArgException
        // Otherwise, execute the command, as wanted QED []
      }      
    }
  }
}
