package driver;

import java.util.Hashtable;

/**
 * This class handles the manuals for each command.
 */
public class Manual {

  // Constant strings for each command message
  private final static String CATMESSAGE = "cat FILE1 ... [> OUTFILE]\n"
      + "\tDisplays the contents of FILE1 and other files"
      + " (i.e. File2 ...) in the shell.";

  private final static String ECHOMESSAGE = "1. echo STRING [> OUTFILE]\n"
      + "\tPrint STRING on the shell, where STRING is a string of "
      + "characters surrounded by double quotations, if OUTFILE is not"
      + " provided.\n\tOtherwise, "
      + "put STRING into file OUTFILE. If OUTFILE already exists, replace "
      + "its contents with STRING.\n\tOtherwise, a new file called OUTFILE"
      + " is created with contents STRING.\n" + "2. echo STRING >> OUTFILE\n"
      + "\tAppends STRING, where STRING is a string of characters "
      + "surrounded by double quotations, to OUTFILE if it exists."
      + "\n\tIf OUTFILE does not exist, "
      + "then a new file called OUTFILE is created with contents STRING";

  private final static String MANMESSAGE = "man CMD [> OUTFILE]\n"
      + "\tPrints the documentation for the command CMD."
      + "\n\tSpecial notes:\n"
      + "\t- [someargument] indicates an optional argument.\n"
      + "\t- '...' indicates a list of arguments.\n"
      + "\t- [> OUTFILE] indicates the file OUTFILE where output from commands"
      + " is redirected.";

  private final static String HISTORYMESSAGE =
      "history [number] [> OUTFILE]\n"
          + "\tPrints out recent commands (both valid and invalid) with one command"
          + " on each line.\n"
          + "\tThe first column is numbered such that the highest number is the "
          + "most recent command.\n"
          + "\tThe second column contains the actual command that was entered.";

  private final static String POPDMESSAGE = "popd\n"
      + "\tRemove the top entry from the directory"
      + "stack, and makes it the current working directory.";

  private final static String PUSHDMESSAGE = "pushd DIR\n"
      + "\tSaves the current working directory by pushing it onto a directory "
      + "stack and then changes the new current working diretory to DIR.";

  private final static String PWDMESSAGE = "pwd [> OUTFILE]\n"
      + "\tPrints the current working directory (including the whole path).";

  private final static String LSMESSAGE =
      "ls [-R] [PATH ...] [> OUTFILE]\n"
          + "\tIf PATH is not given, print the contents (file or directory) of"
          + " the current directory, with a new line following each of the content."
          + "\n\tIf R is given, ls recursively lists all subdirectories. "
          + "\n\tOtherwise, for each path PATH:\n"
          + "\t- If PATH specifies a file, ls prints the file name.\n"
          + "\t- If PATH specifies a directory, ls prints PATH, followed by a"
          + " colon, then the contents of the directory.";

  private final static String CDMESSAGE = "cd DIR\n"
      + "\tChange directory to DIR, which may be relative to the current"
      + " directory or may be a full path.\n" + "\tSpecial keywords:\n"
      + "\t- '..' means a parent directory\n"
      + "\t- '.' means the current directory";

  private final static String MKDIRMESSAGE = "mkdir DIR ...\n"
      + "\tCreates directories, each of which may be relative to the current"
      + " directory or may be a full path.";

  private final static String EXITMESSAGE = "exit\n" + "\tQuits the program.";
  private final static String EXCLAMATIONMESSAGE =
      "!number\n\tRecalls any previously entered command by its history number"
          + " and executes the requested command.";
  private final static String MOVEMESSAGE = "mv OLDPATH NEWPATH\n"
      + "\tMove item at OLDPATH To NEWPATH. Both OLDPATH and NEWPATH may be"
      + "relative to current directory or full paths. If NEWPATH is a "
      + "directory, move the item into the directory.";
  private final static String COPYMESSAGE = "cp OLDPATH NEWPATH\n"
      + "\tCopies items at OLDPATH to NEWPATH. If OLDPATH is a directory, cp "
      + "recursively copies the contents to NEWPATH.\n"
      + "\tBoth OLDPATH and NEWPATH may be relative to current directory or "
      + "full" + "paths.";
  private final static String CURLMESSAGE = "curl URL\n"
      + "\tRetrieves the file at URL and adds it to the current working "
      + "directory in a new file.";
  private final static String GREPMESSAGE =
      "grep [-R] REGEX PATH ... [> OUTFILE] \n\tIf �R is not supplied, grep "
          + "prints any lines containing REGEX in PATH, which must be "
          + "a file. \n\tIf �R is supplied, and PATH is a directory, grep "
          + "recursively traverse the directory "
          + "and, for all lines in all files that contain REGEX, prints the "
          + "path to the file "
          + "(including the filename), then a colon, then the line that "
          + "contained REGEX.";

  // Hasthtable to map commands to each message
  private static Hashtable<String, String> commandMessages;

  /**
   * Returns the message to be printed by the JShell
   * 
   * @param commandName The name of the command to display the manual for
   * @throws CommandException If user inputs an invalid commandname
   */
  public static String printMan(String commandName) throws CommandException {

    // New hashtable mapping command names to respective messages
    commandMessages = new Hashtable<String, String>();
    commandMessages.put("mkdir", MKDIRMESSAGE);
    commandMessages.put("cd", CDMESSAGE);
    commandMessages.put("ls", LSMESSAGE);
    commandMessages.put("pwd", PWDMESSAGE);
    commandMessages.put("pushd", PUSHDMESSAGE);
    commandMessages.put("popd", POPDMESSAGE);
    commandMessages.put("history", HISTORYMESSAGE);
    commandMessages.put("cat", CATMESSAGE);
    commandMessages.put("echo", ECHOMESSAGE);
    commandMessages.put("man", MANMESSAGE);
    commandMessages.put("exit", EXITMESSAGE);
    commandMessages.put("!", EXCLAMATIONMESSAGE);
    commandMessages.put("mv", MOVEMESSAGE);
    commandMessages.put("cp", COPYMESSAGE);
    commandMessages.put("curl", CURLMESSAGE);
    commandMessages.put("grep", GREPMESSAGE);

    // Get the command message from the hashtable
    String commandMessage = commandMessages.get(commandName);

    // If the command message exists, print the message
    if (commandMessage != null) {
      return commandMessage;
    } else {
      // Throw exception for invalid command manuals.
      throw new CommandException(commandName
          + " does not have a command manual.");
    }
  }
}
