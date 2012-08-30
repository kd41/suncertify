package suncertify.socket.client;

import suncertify.socket.MessageHelper;

/**
 * The Class DeleteClient.
 */
public class DeleteClient extends Client {

  protected DeleteClient(String host, int port, long recNo, long lockCookie) {
    super(host, port);
    setMessage(MessageHelper.getDeleteRequestMessage(recNo, lockCookie));
    start();
  }

}
