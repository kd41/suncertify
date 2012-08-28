package suncertify.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
  private static final Logger log = LoggerFactory.getLogger(PropertiesLoader.class);

  private static PropertiesLoader instance;
  private static final String RESOURCE_FILE_NAME = "suncertify.properties";
  private static final String DB_ALONE_LOCATION_KEY = "db.alone.location";
  private static final String DB_SERVER_HOST_KEY = "db.server.host";
  private static final String DB_SERVER_PORT_KEY = "db.server.port";
  private static Properties properties;
  private String dbLocation;
  private String dbHost;
  private String dbPort;

  private PropertiesLoader() {
  }

  public static synchronized PropertiesLoader getInstance() {
    if (instance == null) {
      instance = new PropertiesLoader();
      properties = new Properties();
      FileInputStream is = null;
      try {
        is = new FileInputStream(RESOURCE_FILE_NAME);
        properties.load(is);
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
      dbLocation = properties.containsKey(DB_ALONE_LOCATION_KEY) ? properties.getProperty(DB_ALONE_LOCATION_KEY) : "";
    }
    return dbLocation;
  }

  public String getDbHost() {
    if (this.dbHost == null) {
      dbHost = properties.containsKey(DB_SERVER_HOST_KEY) ? properties.getProperty(DB_SERVER_HOST_KEY) : "";
    }
    return dbHost;
  }

  public String getDbPort() {
    if (this.dbPort == null) {
      dbPort = properties.containsKey(DB_SERVER_PORT_KEY) ? properties.getProperty(DB_SERVER_PORT_KEY) : "";
    }
    return dbPort;
  }

  public void saveProperties(String location, String host, String port) {
    try {
      properties.put(DB_ALONE_LOCATION_KEY, location);
      properties.put(DB_SERVER_HOST_KEY, host);
      properties.put(DB_SERVER_PORT_KEY, port);
      FileOutputStream fos = new FileOutputStream(RESOURCE_FILE_NAME);
      properties.store(fos, null);
      fos.close();
      dbLocation = location;
    } catch (IOException e) {
    }
  }
}
