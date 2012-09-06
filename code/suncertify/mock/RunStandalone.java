package suncertify.mock;

import suncertify.gui.BSJFrame;
import suncertify.program.Mode;

public class RunStandalone {

  public static void main(String[] args) {
    new BSJFrame(Mode.STANDALONE);
  }
}