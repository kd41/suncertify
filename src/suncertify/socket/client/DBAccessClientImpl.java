package suncertify.socket.client;

import static suncertify.constants.Variables.TERMINATOR;

import java.util.ArrayList;
import java.util.List;

import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;
import suncertify.socket.MessageType;

/**
 * The Class DBAccessClientImpl.
 */
public class DBAccessClientImpl implements DBAccessClient {
  private String host;
  private int port;

  /**
   * Instantiates a new database access client implementation.
   * 
   * @param host the host
   * @param port the port
   */
  public DBAccessClientImpl(String host, int port) {
    this.host = host;
    this.port = port;
  }

  @Override
  public String[] readRecord(long recNo) throws RecordNotFoundException {
    String response = new ReadClient(host, port, recNo).getResponse();
    if (response.startsWith(MessageType.ERROR_NO_RECORD.getName())) {
      throw new RecordNotFoundException();
    }
    String[] temp = response.split(TERMINATOR, -1);
    return new String[] { temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], temp[8], temp[9] };
  }

  @Override
  public void updateRecord(long recNo, String[] data, long lockCookie) throws RecordNotFoundException,
                                                                      SecurityException {
    String response = new UpdateClient(host, port, recNo, data, lockCookie).getResponse();
    if (response.startsWith(MessageType.ERROR_NO_RECORD.getName())) {
      throw new RecordNotFoundException();
    } else if (response.startsWith(MessageType.ERROR_SECURITY.getName())) {
      throw new SecurityException();
    }
  }

  @Override
  public void deleteRecord(long recNo, long lockCookie) throws RecordNotFoundException, SecurityException {
    String response = new DeleteClient(host, port, recNo, lockCookie).getResponse();
    if (response.startsWith(MessageType.ERROR_NO_RECORD.getName())) {
      throw new RecordNotFoundException();
    } else if (response.startsWith(MessageType.ERROR_SECURITY.getName())) {
      throw new SecurityException();
    }
  }

  @Override
  public long[] findByCriteria(String[] criteria) {
    String response = "";
    try {
      response = new FindClient(host, port, criteria).getResponse();
    } catch (Exception e) {
      e.printStackTrace();
    }
    String[] temp = response.split(TERMINATOR, -1);
    List<Integer> foundedList = new ArrayList<Integer>();
    for (int i = 1; i < temp.length; i++) {
      try {
        foundedList.add(Integer.parseInt(temp[i]));
      } catch (NumberFormatException e) {
      }
    }
    long[] findedNumbers = new long[foundedList.size()];
    for (int i = 0; i < foundedList.size(); i++) {
      findedNumbers[i] = foundedList.get(i);
    }
    return findedNumbers;
  }

  @Override
  public long createRecord(String[] data) throws DuplicateKeyException {
    String response = new CreateClient(host, port, data).getResponse();
    if (response.startsWith(MessageType.ERROR_DUPLICATE.getName())) {
      throw new DuplicateKeyException();
    }
    String[] temp = response.split(TERMINATOR, -1);
    return Integer.parseInt(temp[1]);
  }

  @Override
  public long lockRecord(long recNo) throws RecordNotFoundException {
    String response = new LockClient(host, port, recNo).getResponse();
    if (response.startsWith(MessageType.ERROR_NO_RECORD.getName())) {
      throw new RecordNotFoundException();
    }
    String[] temp = response.split(TERMINATOR, -1);
    return Integer.parseInt(temp[1]);
  }

  @Override
  public void unlock(long recNo, long cookie) throws SecurityException {
    String response = new UnlockClient(host, port, recNo, cookie).getResponse();
    if (response.startsWith(MessageType.ERROR_SECURITY.getName())) {
      throw new SecurityException();
    }
  }

}
