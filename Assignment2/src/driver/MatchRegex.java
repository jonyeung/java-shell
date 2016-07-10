package driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchRegex {

  /**
   * 
   * @param fileSystem
   * @param commandArgs
   * @return
   * @throws CommandException If file at filepath does not exist or Bad format
   *         of file path
   */
  public static String grep(FileSystem fileSystem, String[] args)
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
      output = x(filepathMatch, regex);
    }


    return output;
  }

  /**
   * 
   * @param fM
   * @param regex
   * @return
   * @throws CommandException
   */
  private static String recursiveGrep(Hashtable<String, File> fM, String regex)
      throws CommandException {

    String output = "";
    ArrayList<String> paths = Collections.list(fM.keys());

    for (int i = 0; i < paths.size(); i++) {

      String currFilepath = paths.get(i);
      File currFile = fM.get(currFilepath);

      if (currFile instanceof TextFile) {
        output += regexMatch(currFile, currFilepath, regex);
      } else if (currFile instanceof Directory) {

        // Get all the files in the currFile directory
        ArrayList<File> storedFiles = ((Directory) currFile).getStoredFiles();

        for (int j = 0; i < storedFiles.size(); j++) {
          File curr = storedFiles.get(i);

          if (currFile instanceof TextFile) {
            String newPath = currFilepath + "/" + currFile.getName();
            output += regexMatch(curr, newPath, regex);
          }
        }

      }



    }



    return output;
  }


  /**
   * 
   * @param fM
   * @param regex
   * @return
   * @throws CommandException
   */
  private static String x(Hashtable<String, File> fM, String regex)
      throws CommandException {

    String output = "";
    ArrayList<String> paths = Collections.list(fM.keys());

    for (int i = 0; i < paths.size(); i++) {
      String currFilepath = paths.get(i);
      output += regexMatch(fM.get(currFilepath), currFilepath, regex);
    }

    return output;
  }



  /**
   * 
   * @param currFile
   * @param filepath
   * @param regex
   * @return
   * @throws CommandException
   */
  private static String regexMatch(File currFile, String filepath, String regex)
      throws CommandException {

    // If the currFile is not a text file then raise an exception
    if (!(currFile instanceof TextFile)) {
      throw new CommandException(filepath + " is not a file.");
    }

    String output = "";
    String[] text = ((TextFile) currFile).getFileContents().split("\n");

    Pattern pattern = Pattern.compile(regex);
    Matcher match;

    // Go through each line in the text file and see if it matches the regex
    for (String line : text) {
      if (line.matches(regex)) {
        output += filepath + ": " + line;
      }
    }

    return output;
  }



}
