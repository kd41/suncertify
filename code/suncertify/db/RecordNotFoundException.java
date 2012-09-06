package suncertify.db;

/**
 * The Class RecordNotFoundException.
 */
public class RecordNotFoundException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new record not found exception.
   */
  public RecordNotFoundException() {
  }

  /**
   * Instantiates a new record not found exception.
   * 
   * @param message the message
   */
  public RecordNotFoundException(String message) {
    super(message);
  }
}
