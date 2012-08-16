package suncertify.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
  private static final Logger log = LoggerFactory.getLogger(PropertiesLoader.class);

  private boolean isAloneMode = true;
  private static PropertiesLoader instance;
  private static final String RESOURCE_FILE_NAME = "suncertify.properties";
  private static final String DB_ALONE_LOCATION_KEY = "db.alone.location";
  private static final String DB_SERVER_LOCATION_KEY = "db.server.location";
  private static Properties resourceBundle;
  private String dbLocation;

  private PropertiesLoader() {
  }

  public static synchronized PropertiesLoader getInstance() {
    if (instance == null) {
      instance = new PropertiesLoader();
      resourceBundle = new Properties();
      FileInputStream is = null;
      try {
        is = new FileInputStream(RESOURCE_FILE_NAME);
        resourceBundle.load(is);
      } catch (IOException e) {
        log.error(e.getMessage(), e);
      } finally {
        if (null != is) {
          try {
            is.close();
          } catch (IOException e) {
          }
        }
      }
    }
    return instance;
  }

  public String getDbLocation() {
    if (this.dbLocation == null) {
      if (isAloneMode) {
        dbLocation = resourceBundle.getProperty(DB_ALONE_LOCATION_KEY);
      } else {
        dbLocation = resourceBundle.getProperty(DB_SERVER_LOCATION_KEY);
      }
    }
    return dbLocation;
  }
}
