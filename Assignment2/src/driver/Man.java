package driver;

public class Man {

  private final static String CATMESSAGE = "cat FILE1 [FILE2 ...]\n"
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

  private final static String MANMESSAGE =
      "man CMD\n" + "\tPrints the documentation for the command CMD."
          + "\n\tSpecial notes:\n"
          + "\t- [someargument] indicates an optional argument.\n"
          + "\t- '...' indicates a list of arguments.";

  private final static String HISTORYMESSAGE = "history [number]\n"
      + "\tPrints out recent commands (both valid and invalid) with one command"
      + " on each line.\n"
      + "\tThe first column is numbered such that the highest number is the "
      + "most recent command.\n"
      + "\tThe second column contains the actual command that was entered.";

  private final static String POPDMESSAGE =
      "popd\n" + "\tRemove the top entry from the directory"
          + "stack, and makes it the current working directory.";

  private final static String PUSHDMESSAGE = "pushd DIR\n"
      + "\tSaves the current working directory by pushing it onto a directory "
      + "stack and then changes the new current working diretory to DIR.";

  private final static String PWDMESSAGE = "pwd\n"
      + "\tPrints the current working directory (including the whole path).";

  private final static String LSMESSAGE = "ls [PATH ...]\n"
      + "\tIf PATH is not given, print the contents (file or directory) of"
      + " the current directory, with a new line following each of the content."
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

  public static void printMan(String commandName) throws CommandException {

    // Check each command name and print the appropriate message
    if (commandName.equals("mkdir")) {
      // mkdir
      System.out.println(MKDIRMESSAGE);
    } else if (commandName.equals("cd")) {
      // cd
      System.out.println(CDMESSAGE);
    } else if (commandName.equals("ls")) {
      // ls
      System.out.println(LSMESSAGE);
    } else if (commandName.equals("pwd")) {
      // pwd
      System.out.println(PWDMESSAGE);
    } else if (commandName.equals("pushd")) {
      // pushd
      System.out.println(PUSHDMESSAGE);
    } else if (commandName.equals("popd")) {
      // popd
      System.out.println(POPDMESSAGE);
    } else if (commandName.equals("history")) {
      // history
      System.out.println(HISTORYMESSAGE);
    } else if (commandName.equals("cat")) {
      // cat
      System.out.println(CATMESSAGE);
    } else if (commandName.equals("echo")) {
      // echo
      System.out.println(ECHOMESSAGE);
    } else if (commandName.equals("man")) {
      // man
      System.out.println(MANMESSAGE);
    } else if (commandName.equals("exit")) {
      // exit
      System.out.println(EXITMESSAGE);
    } else {
      // Throw exception for invalid command manuals.
      throw new CommandException(
          commandName + " does not have a command manual.");
    }
  }
}
