package suncertify.mock;

public class TestData {

  public static String[] getRecord() {
    return new String[] { "some name", "some location", "some specialties", "" + (int) (Math.random() * 10), "$" + (int) (Math.random() * 100) + ".00", "0" };
  }

}
