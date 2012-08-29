package suncertify.socket;

import static suncertify.constants.Variables.TERMINATOR;

public class SocketHelper {
  public static String getReadRequestMessage(long recNo) {
    return MessageType.READ.getName() + TERMINATOR + recNo;
  }

  public static String getReadResponseMessage(String[] data) {
    return MessageType.READ.getName() + getMessage(data);
  }

  private static String getMessage(String[] data) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < data.length; i++) {
      if (i < data.length - 1) {
        sb.append(data[i]).append(TERMINATOR);
      } else {
        sb.append(data[i]);
      }
    }
    return sb.toString();
  }
}
