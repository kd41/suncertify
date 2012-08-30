package suncertify.program;

import suncertify.gui.BSJFrame;

/**
 * The Class Program.
 */
public class Program {
  private Mode mode;

  /**
   * Instantiates a new program.
   * 
   * @param mode the mode
   */
  public Program(Mode mode) {
    this.mode = mode;
  }

  /**
   * The main method.
   * 
   * @param args the arguments
   */
  public static void main(String... args) {
    String mode = args.length > 0 ? args[0] : "";
    if (args.length == 0) {
      new Program(Mode.NETWORK_CLIENT_AND_GUI).run();
    } else if ("server".endsWith(mode)) {
      new Program(Mode.SERVER).run();
    } else if ("alone".equals(mode)) {
      new Program(Mode.STANDALONE).run();
    }
  }

  private void run() {
    new BSJFrame(mode);
  }
}
