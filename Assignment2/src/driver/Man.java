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
  private static String manMessage = "man CMD\n" 
      + "\tPrints the documentation for the command CMD.";
  private static String historyMessage = "history [number]\n"
      + "\tPrints out recent commands (both valid and invalid) with one command"
      + " on each line.\n"
      + "\tThe first column is numbered such that the highest number is the "
      + "most recent command.\n"
      + "\tThe second column contains the actual command that was entered.";
  private static String popdMessage = "popd\n"
      + "\tRemove the top entry from the directory"
      + "stack, and makes it the current working directory.";
  private static String pushdMessage = "pushd DIR\n"
      + "\tSaves the current working directory by pushing it onto a directory "
      + "stack and then changes the new current working diretory to DIR.";

  public static void printMan(String commandName) {

    if (commandName.equals("mkdir")) {
      // mkdir(inputArguments)
      System.out.println("");
    } else if (commandName.equals("cd")) {
      // cd(inputArguments)
      System.out.println("");
    } else if (commandName.equals("ls")) {
      // ls()
      System.out.println("");
    } else if (commandName.equals("pwd")) {
      // pwd()
      System.out.println("");
    } else if (commandName.equals("pushd")) {
      // pushd(inputArguments)
      System.out.println("");
    } else if (commandName.equals("popd")) {
      // popd()
      System.out.println("");
    } else if (commandName.equals("history")) {
      System.out.println(historyMessage);
    } else if (commandName.equals("cat")) {
      System.out.println(catMessage);
    } else if (commandName.equals("echo")) {
      System.out.println(echoMessage);
    } else if (commandName.equals("man")) {
      System.out.println(manMessage);
    }
  }
}
