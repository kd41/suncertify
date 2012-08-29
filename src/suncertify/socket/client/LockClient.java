package suncertify.socket.client;

import suncertify.socket.MessageHelper;

public class LockClient extends Client {

  protected LockClient(String host, int port, long recNo) throws NotInizializedException {
    super(host, port);
    setMessage(MessageHelper.getLockRequestMessage(recNo));
    start();
  }

}
