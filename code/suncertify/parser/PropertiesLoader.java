package suncertify.parser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The Class PropertiesLoader. Read and write the database configuration.
 */
public class PropertiesLoader {

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

  /**
   * Gets the single instance of PropertiesLoader.
   * 
   * @return single instance of PropertiesLoader
   */
  public static synchronized PropertiesLoader getInstance() {
    if (instance == null) {
      instance = new PropertiesLoader();
      properties = new Properties();
      FileInputStream is = null;
      try {
        is = new FileInputStream(RESOURCE_FILE_NAME);
        properties.load(is);
      } catch (IOException e) {
        System.out.println(e);
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

  /**
   * Gets the database location.
   * 
   * @return the database location
   */
  public String getDbLocation() {
    dbLocation = properties.containsKey(DB_ALONE_LOCATION_KEY) ? properties.getProperty(DB_ALONE_LOCATION_KEY) : "";
    return dbLocation;
  }

  /**
   * Gets the database host.
   * 
   * @return the database host
   */
  public String getDbHost() {
    dbHost = properties.containsKey(DB_SERVER_HOST_KEY) ? properties.getProperty(DB_SERVER_HOST_KEY) : "";
    return dbHost;
  }

  /**
   * Gets the database port.
   * 
   * @return the database port
   */
  public String getDbPort() {
    dbPort = properties.containsKey(DB_SERVER_PORT_KEY) ? properties.getProperty(DB_SERVER_PORT_KEY) : "";
    return dbPort;
  }

  /**
   * Save properties.
   * 
   * @param location the location
   * @param host the host
   * @param port the port
   */
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
