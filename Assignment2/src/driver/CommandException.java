package driver;

/**
 * The command exception class for custom exceptions.
 */
@SuppressWarnings("serial")
public class CommandException extends Exception {

  /**
   * Custom exception that initializes the exception's error message
   * 
   * @param errorMessage The error message to be thrown to the user
   */
  public CommandException(String errorMessage) {

    super(errorMessage);
  }
}
