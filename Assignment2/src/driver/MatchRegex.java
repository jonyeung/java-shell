package driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;

/**
 * This class matches regular expressions to lines in a text file
 */
public class MatchRegex {

  /**
   * Returns all lines in each file given that match the regular expressions
   * 
   * @param fileSystem The file system containing all the files and directories
   * @param commandArgs All arguments given
   * @return String All lines in all text files that matched the regex
   * @throws CommandException If file at file path does not exist, Bad format of
   *         file path, a directory file path is given and not recursive
   */
  public static String executeGrep(FileSystem fileSystem, String[] args)
      throws CommandException {

    String output;
    boolean recursiveFlag;
    String regex;
    String[] filepaths;
    // Checks if the recursive option of list is wanted
    if (args.length != 0 && args[0].equalsIgnoreCase("-r")) {
      recursiveFlag = true;
      regex = args[1];
      filepaths = Arrays.copyOfRange(args, 2, args.length);
    } else {
      recursiveFlag = false;
      regex = args[0];
      filepaths = Arrays.copyOfRange(args, 1, args.length);
    }

    // If the regex is not surrounded by double quotes, then raise an exception
    if (regex.charAt(0) != '\"') {
      throw new CommandException(
          "All regex must be enclosed in double quotes.");
    } else {
      // Remove double quotes from regex
      regex = regex.substring(1, regex.length() - 1);
    }

    // Creates a hash table that matches file paths to the corresponding File
    Hashtable<String, File> filepathMatch = new Hashtable<String, File>();

    // If the -r option is given, then put all directories and sub-directories
    // into the hash table, otherwise only put the file paths given into the
    // hash table
    if (recursiveFlag) {
      fileSystem.recursiveDirectoryList(filepaths, filepathMatch);
      output = recursiveGrep(filepathMatch, regex);
    } else {
      fileSystem.DirectoryList(filepaths, filepathMatch);
      output = grep(filepathMatch, regex);
    }

    return output.trim();
  }

  /**
   * Goes through each file in the hashtable and checks if any lines in each
   * file match the regular expression given. If the file is a directory, then
   * go through each of its text files and match their regular expression
   * 
   * @param fM A hash table containing file path that map to corresponding files
   * @param regex The regular expression we want to match
   * @return String All lines in all text files that matched the regex
   * @throws CommandException If any file given is not a text file
   */
  private static String recursiveGrep(Hashtable<String, File> fM, String regex)
      throws CommandException {

    String output = "";
    ArrayList<String> paths = Collections.list(fM.keys());
    Collections.sort(paths);

    for (int i = 0; i < paths.size(); i++) {

      String currFilepath = paths.get(i);
      File currFile = fM.get(currFilepath);

      if (currFile instanceof TextFile) {
        output += regexMatch(currFile, currFilepath, regex);
      } else if (currFile instanceof Directory) {

        // Get all the files in the currFile directory
        ArrayList<File> storedFiles = ((Directory) currFile).getStoredFiles();
        Collections.sort(storedFiles);

        for (int j = 0; j < storedFiles.size(); j++) {
          File curr = storedFiles.get(j);

          if (curr instanceof TextFile) {
            String newPath = currFilepath + "/" + curr.getName();
            output += regexMatch(curr, newPath, regex);
          }
        }
      }
    }
    return output;
  }


  /**
   * Goes through each file in the hashtable and checks if any lines in each
   * file match the regular expression given.
   * 
   * @param fM A hash table containing file path that map to corresponding files
   * @param regex The regular expression we want to match
   * @return String All lines in all text files that matched the regex
   * @throws CommandException If any file given is not a text file
   */
  private static String grep(Hashtable<String, File> fM, String regex)
      throws CommandException {

    String output = "";
    // Get all file paths and sort them alphabetically
    ArrayList<String> paths = Collections.list(fM.keys());
    Collections.sort(paths);

    // Go through each file and call regexMatch on it
    for (int i = 0; i < paths.size(); i++) {
      String currFilepath = paths.get(i);
      output += regexMatch(fM.get(currFilepath), currFilepath, regex);
    }

    return output;
  }

  /**
   * Goes through each line of text in currFile and checks if it it matches the
   * regex. If it does, it will return a string with all matching lines.
   * 
   * @param currFile The current text file we are looking at
   * @param filepath The file path given of the current file
   * @param regex The regular expression we want to match
   * @return String All lines in the text file that matched the regex
   * @throws CommandException If the current file is not a text file
   */
  private static String regexMatch(File currFile, String filepath, String regex)
      throws CommandException {

    // If the currFile is not a text file then raise an exception
    if (!(currFile instanceof TextFile)) {
      throw new CommandException(filepath + " is not a file.");
    }

    String output = "";
    // Get each line in the text file and put it in an array
    String[] text = ((TextFile) currFile).getFileContents().split("\n");

    // Go through each line in the text file and see if it matches the regex
    for (String line : text) {

      // If the line matches the regex then append it to the output
      if (line.matches(regex)) {
        output += filepath + ": " + line;

        // Add a new line character if the matching line doesn't end with one.
        if (!(output.endsWith("\n"))) {
          output += "\n";
        }
      }
    }
    return output;
  }
}
