package suncertify.socket.client;

import suncertify.socket.MessageHelper;

/**
 * The Class CreateClient.
 */
public class CreateClient extends Client {

  protected CreateClient(String host, int port, String[] data) {
    super(host, port);
    setMessage(MessageHelper.getCreateRequestMessage(data));
    start();
  }

}
