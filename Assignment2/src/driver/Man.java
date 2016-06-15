package driver;

public class Man {
  private static String catMessage = "cat FILE1 [FILE2 ...]\n"
      + "\tDisplays the contents of FILE1 and other files"
      + " (i.e. File2 ...) in the shell.";
  private static String echoMessage = "1. echo STRING [> OUTFILE]\n"
      + "\tPrint STRING on the shell, where STRING is a string of "
      + "characters surrounded by double quotations, if OUTFILE is not"
      + " provided.\n\tOtherwise, "
      + "put STRING into file OUTFILE. If OUTFILE already exists, replace "
      + "its contents with STRING.\n\tOtherwise, a new file called OUTFILE"
      + " is created with contents STRING.\n" + "2. echo STRING >> OUTFILE\n"
      + "\tAppends STRING, where STRING is a string of characters "
      + "surrounded by double quotations, to OUTFILE if it exists."
      + "\n\tIf OUTFILE does not exist, "
      + "then a new file called OUTFILE is created with contents STRING\n";
  private static String manMessage =
      "man CMD\n" + "\tPrints the documentation for the command CMD."
          + "\n\tSpecial notes:\n"
          + "\t- [someargument] indicates an optional argument.\n"
          + "\t- '...' indicates a list of arguments.";
  private static String historyMessage = "history [number]\n"
      + "\tPrints out recent commands (both valid and invalid) with one command"
      + " on each line.\n"
      + "\tThe first column is numbered such that the highest number is the "
      + "most recent command.\n"
      + "\tThe second column contains the actual command that was entered.";
  private static String popdMessage =
      "popd\n" + "\tRemove the top entry from the directory"
          + "stack, and makes it the current working directory.";
  private static String pushdMessage = "pushd DIR\n"
      + "\tSaves the current working directory by pushing it onto a directory "
      + "stack and then changes the new current working diretory to DIR.";
  private static String pwdMessage = "pwd\n"
      + "\tPrints the current working directory (including the whole path).";
  private static String lsMessage = "ls [PATH ...]\n"
      + "\tIf PATH is not given, print the contents (file or directory) of"
      + " the current directory, with a new line following each of the content."
      + "\n\tOtherwise, for each path PATH:\n"
      + "\t- If PATH specifies a file, ls prints the file name.\n"
      + "\t- If PATH specifies a directory, ls prints PATH, followed by a"
      + " colon, then the contents of the directory.";
  private static String cdMessage = "cd DIR\n"
      + "\tChange directory to DIR, which may be relative to the current"
      + " directory or may be a full path.\n" + "\tSpecial keywords:\n"
      + "\t- '..' means a parent directory\n"
      + "\t- '.' means the current directory\n";
  private static String mkdirMessage = "mkdir DIR ...\n"
      + "\tCreates directories, each of which may be relative to the current"
      + " directory or may be a full path.";
  private static String exitMessage = "exit\n" + "\tQuits the program.";

  public static void printMan(String commandName) {
    // Check each command name and print the appropriate message
    if (commandName.equals("mkdir")) {
      // mkdir
      System.out.println(mkdirMessage);
    } else if (commandName.equals("cd")) {
      // cd
      System.out.println(cdMessage);
    } else if (commandName.equals("ls")) {
      // ls
      System.out.println(lsMessage);
    } else if (commandName.equals("pwd")) {
      // pwd
      System.out.println(pwdMessage);
    } else if (commandName.equals("pushd")) {
      // pushd
      System.out.println(pushdMessage);
    } else if (commandName.equals("popd")) {
      // popd
      System.out.println(popdMessage);
    } else if (commandName.equals("history")) {
      // history
      System.out.println(historyMessage);
    } else if (commandName.equals("cat")) {
      // cat
      System.out.println(catMessage);
    } else if (commandName.equals("echo")) {
      // echo
      System.out.println(echoMessage);
    } else if (commandName.equals("man")) {
      // man
      System.out.println(manMessage);
    } else if (commandName.equals("exit")) {
      // exit
      System.out.println(exitMessage);
    }
  }
}
