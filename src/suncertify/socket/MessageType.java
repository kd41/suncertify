package suncertify.socket;

public enum MessageType {
  READ("read"), FIND("find"), GET("get"), CREATE("create"), UPDATE("update"), DELETE("delete"), LOCK("lock"), UNLOCK(
      "unlock"), ERROR_NO_RECORD("e_no_rec"), ERROR_SECURITY("e_security"), ERROR_DUPLICATE("e_duplicate"), ERROR_CONNECTION(
      "e_connection");

  private String name;

  private MessageType(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
