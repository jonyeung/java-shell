package driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;

/**
 * This class returns the contents of a file
 */
public class List {

  /**
   * Returns the contents of each file in file paths.
   * 
   * @param fileSystem The file system containing all the files and directories
   * @param filepaths The file paths that we have to we want to list contents of
   * @return String The contents of each file path given
   * @throws CommandException
   */
  public static String list(FileSystem fileSystem, String[] args)
      throws CommandException {

    String output;
    boolean recursiveFlag;
    String[] filepaths;
    // Checks if the recursive option of list is wanted
    if (args != null && args[0].equalsIgnoreCase("-r")) {
      recursiveFlag = true;
      filepaths = Arrays.copyOfRange(args, 1, args.length);
    } else {
      recursiveFlag = false;
      filepaths = args;
    }

    // If no file paths given then return contents of current directory
    if (filepaths == null) {
      output = listContents(fileSystem, fileSystem.getCurrentDirectory());

    } else {

      // Creates a hash table that matches file paths to the corresponding File
      Hashtable<String, File> filepathMatch = new Hashtable<String, File>();

      // If the -r option is given, then put all directories and sub-directories
      // into the hash table, otherwise only put the file paths given into the
      // hash table
      if (recursiveFlag) {
        recursiveList(fileSystem, filepaths, filepathMatch);
      } else {
        notRecursiveList(fileSystem, filepaths, filepathMatch);
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
   * want to ls
   * 
   * @param fileSystem The file system containing all the files and directories
   * @param files An array list of all file paths that we want to list
   * @param fm A hash table containing file path that map to corresponding files
   * @return String The wanted output when listing multiple files
   * @throws CommandException
   */
  private static String listAll(FileSystem fileSystem, ArrayList<String> paths,
      Hashtable<String, File> fm) throws CommandException {

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
   * Returns a list of all directories and their subdirectories in filepaths
   * 
   * @param fileSystem The file system containing all the files and directories
   * @param filepaths The filepaths that we want to list recursivly
   * @param fm A hash table containing file path that map to corresponding files
   * @throws CommandException
   */
  private static void recursiveList(FileSystem fileSystem, String[] filepaths,
      Hashtable<String, File> fm) throws CommandException {

    // Loop through each file path given
    for (String path : filepaths) {

      // Get the file at path
      File currFile = fileSystem.getFile(path);

      // If the curr is a directory, add all sub-directories to the hash table
      if (currFile instanceof Directory) {
        fileSystem.recurseDirectories((Directory) currFile, path, fm);
      }
    }
  }

  /**
   * Returns an array list of files in filepaths
   * 
   * @param fileSystem The file system containing all the files and directories
   * @param filepaths The filepaths that we want to list
   * @param fm A hash table containing file path that map to corresponding files
   * @throws CommandException
   */
  private static void notRecursiveList(FileSystem fileSystem,
      String[] filepaths, Hashtable<String, File> fm) throws CommandException {

    // Loop through each file path given
    for (String path : filepaths) {

      // Get the file at path and add it to the hash table
      File currFile = fileSystem.getFile(path);
      fm.put(path, currFile);
    }
  }

  /**
   * Returns the contents of the directory given
   * 
   * @param fileSystem The file system containing all the files and directories
   * @param dir The directory that we want to return its contents
   * @return String The contents of the directory
   * @throws CommandException
   */
  private static String listContents(FileSystem fileSystem, Directory dir)
      throws CommandException {

    String output = "";

    // Return all the elements in the storedFiles found in dir
    for (File file : dir.getStoredFiles()) {
      output += file.getName() + "\n";
    }

    if (!output.equals("")) {
      output = output.substring(0, output.length() - 1);
    }
    return output;
  }
}
