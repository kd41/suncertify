package suncertify.socket.client;

import suncertify.socket.MessageHelper;

public class FindClient extends Client {

  protected FindClient(String host, int port, String[] criteria) {
    super(host, port);
    setMessage(MessageHelper.getFindByCriteriaRequestMessage(criteria));
    start();
  }
}
