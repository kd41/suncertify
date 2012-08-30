package suncertify.socket.client;

import suncertify.socket.MessageHelper;

public class UnlockClient extends Client {

  protected UnlockClient(String host, int port, long recNo, long cookie) {
    super(host, port);
    setMessage(MessageHelper.getUnlockRequestMessage(recNo, cookie));
    start();
  }

}
