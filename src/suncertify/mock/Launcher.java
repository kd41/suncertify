package suncertify.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import suncertify.gui.BSJFrame;
import suncertify.program.Mode;

public class Launcher {
  private static final Logger log = LoggerFactory.getLogger(Launcher.class);

  public static void main(String... args) {
    Launcher l = new Launcher();
    l.launchStandalone();
  }

  private void launchStandalone() {
    new BSJFrame(Mode.STANDALONE);
  }

  private void launchServer() {
    new BSJFrame(Mode.SERVER);
  }

  private void launchNetworkAndGUI() {
    new BSJFrame(Mode.NETWORK_CLIENT_AND_GUI);
  }

}
