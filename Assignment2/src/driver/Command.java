package driver;

public interface Command {

  public void execute(FileSystem fileSystem, String[] commandArgs);
}
