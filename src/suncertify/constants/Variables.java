package suncertify.constants;

public class Variables {
  public static final String ENCODING = "US-ASCII";
  public static final String FILE_PATH = "C:\\projects\\sun\\suncertify\\";
  public static final String FILE_NAME = "db-2x3-ext.db";
  public static final String FILE_NAME_TEMP = "db-2x3-ext-temp.db";
  public static final String TERMINATOR = "\0";

  public static String getFullFilePath() {
    return FILE_PATH + FILE_NAME;
  }

  public static String getFullFilePathTemp() {
    return FILE_PATH + FILE_NAME_TEMP;
  }

  public static String getOriginalFilePath() {
    return getFullFilePath();
  }

  public static String getWorkedFilePath() {
    return getFullFilePathTemp();
  }
}
