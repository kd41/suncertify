package suncertify.mock;

import suncertify.gui.BSJFrame;
import suncertify.program.Mode;

public class RunClient {

  public static void main(String[] args) {
    new BSJFrame(Mode.NETWORK_CLIENT_AND_GUI);
  }
}