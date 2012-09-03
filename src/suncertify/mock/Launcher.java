package suncertify.mock;

import suncertify.gui.BSJFrame;
import suncertify.program.Mode;

public class Launcher {

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
