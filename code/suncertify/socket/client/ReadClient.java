package suncertify.socket.client;

import suncertify.socket.MessageHelper;

/**
 * The Class ReadClient provides the read record.
 */
public class ReadClient extends Client {

  protected ReadClient(String host, int port, long recNo) {
    super(host, port);
    setMessage(MessageHelper.getReadRequestMessage(recNo));
    start();
  }
}
