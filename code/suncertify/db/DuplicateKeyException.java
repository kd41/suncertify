package suncertify.db;

/**
 * The Class DuplicateKeyException.
 */
public class DuplicateKeyException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new duplicate key exception.
   */
  public DuplicateKeyException() {
  }

  /**
   * Instantiates a new duplicate key exception.
   * 
   * @param message the message
   */
  public DuplicateKeyException(String message) {
    super(message);
  }

}
