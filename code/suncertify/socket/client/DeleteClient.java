package suncertify.socket.client;

import suncertify.socket.MessageHelper;

/**
 * The Class DeleteClient provides the delete record.
 */
public class DeleteClient extends Client {

  protected DeleteClient(String host, int port, long recNo, long lockCookie) {
    super(host, port);
    setMessage(MessageHelper.getDeleteRequestMessage(recNo, lockCookie));
    start();
  }

}
