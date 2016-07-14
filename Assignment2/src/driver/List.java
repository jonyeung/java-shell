package driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;

/**
 * This class returns the contents of a directory
 */
public class List {

  /**
   * Returns the contents of each file in file paths.
   * 
   * @param fileSystem The file system containing all the files and directories
   * @param args All arguments given
   * @return String The contents of each file path given
   * @throws CommandException If file at file path does not exist
   */
  public static String list(FileSystem fileSystem, String[] args)
      throws CommandException {

    String output;
    boolean recursiveFlag;
    String[] filepaths;
    // Checks if the recursive option of list is wanted
    if (args.length != 0 && args[0].equalsIgnoreCase("-r")) {
      recursiveFlag = true;
      filepaths = Arrays.copyOfRange(args, 1, args.length);
      // Add current directory to file paths if no other file paths are given
      if (filepaths.length == 0) {
        filepaths = new String[] {"."};
      }
    } else {
      recursiveFlag = false;
      filepaths = args;
    }

    // If no file paths given then return contents of current directory
    if (filepaths.length == 0) {
      output = listContents(fileSystem, fileSystem.getCurrentDirectory());

    } else {

      // Creates a hash table that matches file paths to the corresponding File
      Hashtable<String, File> filepathMatch = new Hashtable<String, File>();

      // If the -r option is given, then put all directories and sub-directories
      // into the hash table, otherwise only put the file paths given into the
      // hash table
      if (recursiveFlag) {
        fileSystem.recursiveDirectoryList(filepaths, filepathMatch);
      } else {
        fileSystem.DirectoryList(filepaths, filepathMatch);
      }

      // sort the file paths by alphabetical order
      ArrayList<String> paths = Collections.list(filepathMatch.keys());
      Collections.sort(paths);

      output = listAll(fileSystem, paths, filepathMatch);
    }
    return output;
  }

  /**
   * Returns the wanted output when the user gives a list of files that they
   * want to list
   * 
   * @param fileSystem The file system containing all the files and directories
   * @param files An array list of all file paths that we want to list
   * @param fm A hash table containing file path that map to corresponding files
   * @return String The wanted output when listing multiple files
   */
  private static String listAll(FileSystem fileSystem, ArrayList<String> paths,
      Hashtable<String, File> fm) {

    String outputDirectories = "";
    String outputFiles = "";
    String output;

    // Loop through each file
    for (int i = 0; i < paths.size(); i++) {

      String currFilePath = paths.get(i);
      File currFile = fm.get(currFilePath);

      // If the current file is a directory then add the contents of the
      // directory to the directory output string
      if (currFile instanceof Directory) {

        String contents = listContents(fileSystem, (Directory) currFile);
        outputDirectories += "\n\n" + currFilePath + ":";
        if (!contents.equals("")) {
          outputDirectories += "\n" + contents;
        }
      } else {
        // Otherwise add the name of the current file to file output string
        outputFiles += currFilePath + " ";
      }
    }
    // combine outputFiles and outputDirectories
    if (outputFiles.equals("")) {
      output = outputDirectories.trim();
    } else {
      output = outputFiles.trim() + outputDirectories;
    }
    return output;
  }

  /**
   * Returns the contents of the directory given
   * 
   * @param fileSystem The file system containing all the files and directories
   * @param dir The directory that we want to return its contents
   * @return String The contents of the directory
   */
  private static String listContents(FileSystem fileSystem, Directory dir) {

    String output = "";

    // Return all the elements in the storedFiles found in dir
    for (File file : dir.getStoredFiles()) {
      output += file.getName() + "\n";
    }

    return output.trim();
  }
}
