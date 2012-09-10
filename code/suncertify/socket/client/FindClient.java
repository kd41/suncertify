package suncertify.socket.client;

import java.net.ConnectException;

import suncertify.socket.MessageHelper;

/**
 * The Class FindClient provides the find records.
 */
public class FindClient extends Client {

  protected FindClient(String host, int port, String[] criteria) throws ConnectException {
    super(host, port);
    setMessage(MessageHelper.getFindByCriteriaRequestMessage(criteria));
    start();
  }
}
