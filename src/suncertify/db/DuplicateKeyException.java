package suncertify.db;

public class DuplicateKeyException extends Exception {
  private static final long serialVersionUID = 1L;

  public DuplicateKeyException() {
  }

  public DuplicateKeyException(String message) {
    super(message);
  }

}
