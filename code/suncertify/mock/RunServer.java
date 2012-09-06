package suncertify.mock;

import suncertify.gui.BSJFrame;
import suncertify.program.Mode;

public class RunServer {

  public static void main(String[] args) {
    new BSJFrame(Mode.SERVER);
  }
}