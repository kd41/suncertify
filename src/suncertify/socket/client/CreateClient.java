package suncertify.socket.client;

import suncertify.socket.MessageHelper;

public class CreateClient extends Client {

  protected CreateClient(String host, int port, String[] data) throws NotInizializedException {
    super(host, port);
    setMessage(MessageHelper.getCreateRequestMessage(data));
    start();
  }

}
