package suncertify.parser;

import java.util.ArrayList;
import java.util.List;

import suncertify.constants.Variables;


public class DBPresenter {
  private static DBPresenter instance;

  private String dbPath;
  private int magicCookie;
  private int fieldsNumber;
  private List<DBRecord> records;

  private DBPresenter(String dbPath) {
    this.dbPath = dbPath;
  }

  public static synchronized DBPresenter getInstance() {
    if (instance == null) {
      instance = new DBPresenter(Variables.getWorkedFilePath());
    }
    return instance;
  }

  public long getMagicCookie() {
    return magicCookie;
  }

  public void setMagicCookie(int magicCookie) {
    this.magicCookie = magicCookie;
  }

  public long getFieldsNumber() {
    return fieldsNumber;
  }

  public void setFieldsNumber(int fieldsNumber) {
    this.fieldsNumber = fieldsNumber;
  }

  public List<DBRecord> getRecords() {
    if (records == null) {
      records = new ArrayList<DBRecord>();
    }
    return records;
  }

  public void setRecords(List<DBRecord> records) {
    this.records = records;
  }

  public String getDbPath() {
    return dbPath;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("DBPresenter[");
    sb.append("dbPath=").append(dbPath);
    sb.append(", magicCookie=").append(magicCookie);
    sb.append(", fieldsNumber=").append(fieldsNumber).append("\n");
    for (DBRecord record : getRecords()) {
      sb.append(", ").append(record).append("\n");
    }
    return sb.toString();
  }
}
