package suncertify.socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import suncertify.constants.Variables;

import suncertify.parser.PropertiesLoader;

import suncertify.socket.MessageHelper;

public class FindClient extends Client {
  private static final Logger log = LoggerFactory.getLogger(FindClient.class);

  protected FindClient(String host, int port, String[] criteria) {
    super(host, port);
    setMessage(MessageHelper.getFindByCriteriaRequestMessage(criteria));
    start();
  }

  // TODO: delete after
  public static void main(String args[]) throws NotInizializedException {
    FindClient fc = new FindClient(PropertiesLoader.getInstance().getDbHost(),
                                   Integer.parseInt(PropertiesLoader.getInstance().getDbPort()), new String[] { "Bi",
                                                                                                               "", "",
                                                                                                               "", "",
                                                                                                               "" });
    String[] ids = fc.getResponse().split(Variables.TERMINATOR, -1);
    for (String id : ids) {
      log.info("found id: " + id);
    }
  }
}
