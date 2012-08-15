package suncertify.parser;

import java.util.ResourceBundle;

public class PropertiesLoader {
  private boolean isAloneMode = true;
  private static PropertiesLoader instance;
  private static final String RESOURCE_FILE_NAME = "suncertify";
  private static final String DB_ALONE_LOCATION_KEY = "db.alone.location";
  private static final String DB_SERVER_LOCATION_KEY = "db.server.location";
  private static ResourceBundle resourceBundle;
  private String dbLocation;

  private PropertiesLoader() {
  }

  public static synchronized PropertiesLoader getInstance() {
    if (instance == null) {
      instance = new PropertiesLoader();
      resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_NAME);
    }
    return instance;
  }

  public String getDbLocation() {
    if (this.dbLocation == null) {
      if (isAloneMode) {
        dbLocation = resourceBundle.getString(DB_ALONE_LOCATION_KEY);
      } else {
        dbLocation = resourceBundle.getString(DB_SERVER_LOCATION_KEY);
      }
    }
    return dbLocation;
  }
}
