package suncertify.socket;

import static suncertify.constants.Variables.TERMINATOR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHelper {
  private static final Logger log = LoggerFactory.getLogger(MessageHelper.class);

  public static String getReadRequestMessage(long recNo) {
    return MessageType.READ.getName() + TERMINATOR + recNo;
  }

  public static String getReadResponseMessage(String[] data) {
    return MessageType.READ.getName() + TERMINATOR + getMessage(data);
  }

  public static String getFindByCriteriaRequestMessage(String[] criteria) {
    return MessageType.FIND.getName() + TERMINATOR + getFindMessage(criteria);
  }

  public static String getFindByCriteriaResponseMessage(long[] recNos) {
    return MessageType.FIND.getName() + TERMINATOR + getMessage(recNos);
  }

  public static String getUpdateRequestMessage(long recNo, String[] data, long lockCookie) {
    return MessageType.UPDATE.getName() + TERMINATOR + recNo + TERMINATOR + getMessage(data) + TERMINATOR + lockCookie;
  }

  public static String getUpdateResponseMessage() {
    return MessageType.UPDATE.getName();
  }

  public static String getDeleteRequestMessage(long recNo, long lockCookie) {
    return MessageType.DELETE.getName() + TERMINATOR + recNo + TERMINATOR + lockCookie;
  }

  public static String getDeleteResponseMessage() {
    return MessageType.DELETE.getName();
  }

  public static String getCreateRequestMessage(String[] data) {
    return MessageType.CREATE.getName() + TERMINATOR + getMessage(data);
  }

  public static String getCreateResponseMessage(long recNo) {
    return MessageType.CREATE.getName() + TERMINATOR + recNo;
  }

  public static String getLockRequestMessage(long recNo) {
    return MessageType.LOCK.getName() + TERMINATOR + recNo;
  }

  public static String getLockResponseMessage(long cookie) {
    return MessageType.LOCK.getName() + TERMINATOR + cookie;
  }

  public static String getUnlockRequestMessage(long recNo, long cookie) {
    return MessageType.UNLOCK.getName() + TERMINATOR + recNo + TERMINATOR + cookie;
  }

  public static String getUnlockResponseMessage() {
    return MessageType.UNLOCK.getName();
  }

  public static String getSecurityError() {
    return MessageType.ERROR_SECURITY.getName();
  }

  public static String getNoRecordError() {
    return MessageType.ERROR_NO_RECORD.getName();
  }

  public static String getDuplicateError() {
    return MessageType.ERROR_DUPLICATE.getName();
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

  private static String getFindMessage(String[] data) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < data.length; i++) {
      if (data[i] == null) {
        sb.append(TERMINATOR);
      } else {
        if (i < data.length - 1) {
          sb.append(data[i]).append(TERMINATOR);
        } else {
          sb.append(data[i]);
        }
      }
    }
    return sb.toString();
  }

  private static String getMessage(long[] data) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < data.length; i++) {
      log.info("i: " + data[i]);
      if (i < data.length - 1) {
        sb.append(data[i]).append(TERMINATOR);
      } else {
        sb.append(data[i]);
      }
    }
    return sb.toString();
  }
}
