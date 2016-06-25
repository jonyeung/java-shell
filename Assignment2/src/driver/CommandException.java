package driver;

// TODO Needs discussion. Refer to Monday, June 19th B07 lecture on exceptions

/**
 * The Command Exception class.
 */
@SuppressWarnings("serial")
public class CommandException extends Exception {

  public CommandException(String errorMessage) {

    super(errorMessage);
  }
}
