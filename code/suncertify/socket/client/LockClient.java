package suncertify.socket.client;

import suncertify.socket.MessageHelper;

/**
 * The Class LockClient provides the lock record.
 */
public class LockClient extends Client {

  protected LockClient(String host, int port, long recNo) {
    super(host, port);
    setMessage(MessageHelper.getLockRequestMessage(recNo));
    start();
  }

}
