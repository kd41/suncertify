package suncertify.socket.client;

import suncertify.socket.MessageHelper;

public class DeleteClient extends Client {

  protected DeleteClient(String host, int port, long recNo, long lockCookie) throws NotInizializedException {
    super(host, port);
    setMessage(MessageHelper.getDeleteRequestMessage(recNo, lockCookie));
    start();
  }

}
