package suncertify.parser;

import java.util.ArrayList;
import java.util.List;

import suncertify.db.RecordNotFoundException;

public class DBPresenter {
  private static DBPresenter instance;

  private String dbPath;
  private String dbHost;
  private String dbPort;
  private int magicCookie;
  private byte[] fileHeader;
  private int fieldsNumber;
  private List<DBRecord> records;
  private long newRecordNumber;

  private DBPresenter(String dbPath, String dbHost, String dbPort) {
    this.dbPath = dbPath;
    this.dbHost = dbHost;
    this.dbPort = dbPort;
  }

  public static synchronized DBPresenter getInstance() {
    if (instance == null || !instance.getDbPath().equals(PropertiesLoader.getInstance().getDbLocation())
        || !instance.getDbHost().equals(PropertiesLoader.getInstance().getDbHost())
        || !instance.getDbPort().equals(PropertiesLoader.getInstance().getDbPort())) {
      instance = new DBPresenter(PropertiesLoader.getInstance().getDbLocation(), PropertiesLoader.getInstance()
                                                                                                 .getDbHost(),
                                 PropertiesLoader.getInstance().getDbPort());
      DBReaderWriter.createDatabasePresenter();
    }
    return instance;
  }

  public long getMagicCookie() {
    return magicCookie;
  }

  public void setMagicCookie(int magicCookie) {
    this.magicCookie = magicCookie;
  }

  public void setFileHeader(byte[] fileHeader) {
    this.fileHeader = fileHeader;
  }

  public byte[] getFileHeader() {
    return fileHeader;
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

  public String getDbHost() {
    return dbHost;
  }

  public String getDbPort() {
    return dbPort;
  }

  public void setNewRecordNumber(long newRecordNumber) {
    this.newRecordNumber = newRecordNumber;
  }

  public long getNewRecordNumber() {
    return newRecordNumber;
  }

  public void addRecord(DBRecord record) {
    record.setPosition(newRecordNumber++);
    getRecords().add(record);
  }

  public DBRecord getRecord(long recNo) throws RecordNotFoundException {
    return getRecord(recNo, true);
  }

  public DBRecord getRecord(long recNo, boolean checkValid) throws RecordNotFoundException {
    for (DBRecord record : getRecords()) {
      if (record.getPosition() == recNo && (!checkValid || record.isValid())) {
        return record;
      }
    }
    throw new RecordNotFoundException("No record found with number: " + recNo);
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("DBPresenter[");
    sb.append("dbPath=").append(dbPath);
    sb.append(", dbHost=").append(dbHost);
    sb.append(", dbPort=").append(dbPort);
    sb.append(", magicCookie=").append(magicCookie);
    sb.append(", fieldsNumber=").append(fieldsNumber).append("\n");
    for (DBRecord record : getRecords()) {
      sb.append(", ").append(record).append("\n");
    }
    return sb.toString();
  }
}
