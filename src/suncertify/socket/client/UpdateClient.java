package suncertify.socket.client;

import suncertify.socket.MessageHelper;

public class UpdateClient extends Client {

  protected UpdateClient(String host, int port, long recNo, String[] data, long lockCookie) {
    super(host, port);
    setMessage(MessageHelper.getUpdateRequestMessage(recNo, data, lockCookie));
    start();
  }

}
