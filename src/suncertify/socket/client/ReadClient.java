package suncertify.socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import suncertify.socket.MessageHelper;

import suncertify.parser.PropertiesLoader;

public class ReadClient extends Client {
  private static final Logger log = LoggerFactory.getLogger(ReadClient.class);

  public ReadClient(String host, int port, long recNo) {
    super(host, port);
    setMessage(MessageHelper.getReadRequestMessage(recNo));
    start();
    log.info("response: " + getResponse());
  }

  // TODO: delete after
  public static void main(String args[]) throws NotInizializedException {
    for (int i = 0; i < 100; i++) {
      new ReadClient(PropertiesLoader.getInstance().getDbHost(), Integer.parseInt(PropertiesLoader.getInstance()
                                                                                                  .getDbPort()), i);
    }
  }
}
