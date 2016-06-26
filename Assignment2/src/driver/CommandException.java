package driver;

/**
 * The Command Exception class.
 */
@SuppressWarnings("serial")
public class CommandException extends Exception {

  public CommandException(String errorMessage) {

    super(errorMessage);
  }
}
