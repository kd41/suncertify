package suncertify.parser;

import static java.lang.System.out;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import suncertify.constants.StringPool;
import suncertify.constants.Variables;

public class DBReaderWriter {
  private static final Logger log = LoggerFactory.getLogger(DBReaderWriter.class);

  private static final int VALID_LENGTH = 1;
  private static final int NAME_LENGTH = 32;
  private static final int LOCATION_LENGTH = 64;
  private static final int SPECIALTIES_LENGTH = 64;
  private static final int NUM_OF_WOKERS_LENGTH = 6;
  private static final int RATE_LENGTH = 8;
  private static final int OWNER_LENGTH = 8;

  public static DBPresenter createDatabasePresenter() {
    DBPresenter presenter = DBPresenter.getInstance();
    File file = new File(presenter.getDbPath());

    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;
    try {
      fis = new FileInputStream(file);
      bis = new BufferedInputStream(fis);
      dis = new DataInputStream(bis);

      int magicCookie = dis.readInt();
      presenter.setMagicCookie(magicCookie);

      int fieldsNumber = dis.readUnsignedShort();
      log.info("fieldsNumber: {}", fieldsNumber);
      presenter.setFieldsNumber(fieldsNumber);
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < fieldsNumber; i++) {
        int recordNameLength = dis.readUnsignedByte();
        log.info("recordNameLength: {}", recordNameLength);
        for (int j = 0; j < recordNameLength; j++) {
          char c = (char) dis.readUnsignedByte();
          sb.append(c);
        }
        int fieldLength = dis.readUnsignedByte();
        log.info("Field name: '{}'\tField length: {}", sb.toString(), fieldLength);
        sb.setLength(0);
      }
      log.info("fileHeader: " + presenter.getFileHeader());
      try {
        int count = 0;
        while (true) {
          Record r = readNextRecord(dis);
          DBRecord record = new DBRecord();
          record.setPosition(++count);
          record.setValid(r.getValid());
          record.setName(r.getName());
          record.setLocation(r.getLocation());
          record.setSpecialties(r.getSpecialities());
          record.setNumberOfWorkers(r.getNumberOfWorkers());
          record.setRate(r.getRate());
          record.setOwner(r.getOwner());
          presenter.getRecords().add(record);
        }
      } catch (EOFException e) {
      }

      File f1 = new File(PropertiesLoader.getInstance().getDBLocation());
      InputStream in = new FileInputStream(f1);
      byte[] buf = new byte[54];
      in.read(buf);
      DBPresenter.getInstance().setFileHeader(buf);
      in.close();

      out.printf("Successful: " + new Date().toString());
      presenter.setNewRecordNumber(presenter.getRecords().size() + 1);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    } finally {
      try {
        dis.close();
      } catch (Exception e) {
      }
      try {
        bis.close();
      } catch (Exception e) {
      }
      try {
        fis.close();
      } catch (Exception e) {
      }
    }
    return presenter;
  }

  public static long addRecord(String[] data) throws Exception {
    File file = new File(DBPresenter.getInstance().getDbPath());
    // log.info("write to dbPath: {}", DBPresenter.getInstance().getDbPath());
    FileWriter fw = new FileWriter(file, true);
    writeRecord(data, fw);
    fw.flush();
    fw.close();
    DBPresenter.getInstance().addRecord(DBRecordHelper.createDBRecord(data));
    return DBPresenter.getInstance().getNewRecordNumber();
  }

  private static void writeRecord(DBRecord record, FileWriter fw) throws Exception {
    if (record.isValid()) {
      writeNext(fw, "", VALID_LENGTH);// valid
    } else {
      writeNext(fw, (byte) -1, VALID_LENGTH);// valid
    }
    writeNext(fw, record.getName(), NAME_LENGTH);
    writeNext(fw, record.getLocation(), LOCATION_LENGTH);
    writeNext(fw, record.getSpecialties(), SPECIALTIES_LENGTH);
    writeNext(fw, record.getNumberOfWorkers(), NUM_OF_WOKERS_LENGTH);
    writeNext(fw, record.getRate(), RATE_LENGTH);
    writeNext(fw, record.getOwner(), OWNER_LENGTH);
  }

  private static void writeRecord(String[] data, FileWriter fw) throws Exception {
    writeNext(fw, "", VALID_LENGTH);// valid
    for (int i = 0; i < data.length; i++) {
      String d = data[i];
      switch (i) {
      case 0:// name
        writeNext(fw, d, NAME_LENGTH);
        break;
      case 1:// location
        writeNext(fw, d, LOCATION_LENGTH);
        break;
      case 2:// specialties
        writeNext(fw, d, SPECIALTIES_LENGTH);
        break;
      case 3:// number of workers
        writeNext(fw, d, NUM_OF_WOKERS_LENGTH);
        break;
      case 4:// rate
        writeNext(fw, d, RATE_LENGTH);
        break;
      case 5:// owner
        writeNext(fw, d, OWNER_LENGTH);
        break;
      }
    }
  }

  public static void updateRecord(DBRecord record) throws Exception {
    saveDBPresenter();
  }

  public static void deleteRecord(DBRecord record) throws Exception {
    record.setValid((byte) -1);
    saveDBPresenter();
  }

  public static void saveDBPresenter() throws Exception {
    File file = new File(DBPresenter.getInstance().getDbPath());
    FileWriter fw = new FileWriter(file, false);
    byte[] header = DBPresenter.getInstance().getFileHeader();
    char[] headerChars = new char[header.length];
    for (int i = 0; i < header.length; i++) {
      headerChars[i] = (char) header[i];
    }
    fw.write(headerChars);
    for (DBRecord record : DBPresenter.getInstance().getRecords()) {
      writeRecord(record, fw);
    }
    fw.flush();
    fw.close();
  }

  private static void writeNext(FileWriter fw, String data, int maxLength) throws Exception {
    if (fw == null) {
      throw new RuntimeException("FileWriter can't be null!");
    }
    if (data == null || StringPool.BLANK.equals(data)) {
      fw.write(Variables.TERMINATOR);
    } else if (data.length() <= maxLength) {
      fw.write(data);
      if (data.length() < maxLength) {
        fw.write(Variables.TERMINATOR);
      }
    } else {
      fw.write(data.substring(0, maxLength));
    }
  }

  private static void writeNext(FileWriter fw, byte _byte, int maxLength) throws Exception {
    if (fw == null) {
      throw new RuntimeException("FileWriter can't be null!");
    }
    fw.write(new char[] { (char) _byte });
  }

  private static Record readNextRecord(DataInputStream dis) throws IOException {
    Record record = new Record();
    String validStr = readStringField(dis, VALID_LENGTH);
    record.setValid("?".equals(validStr) ? (byte) -1 : (byte) 0);
    record.setName(readStringField(dis, NAME_LENGTH));
    record.setLocation(readStringField(dis, LOCATION_LENGTH));
    record.setSpecialities(readStringField(dis, SPECIALTIES_LENGTH));
    record.setNumberOfWorkers(readStringField(dis, NUM_OF_WOKERS_LENGTH));
    record.setRate(readStringField(dis, RATE_LENGTH));
    record.setOwner(readStringField(dis, OWNER_LENGTH));
    return record;
  }

  private static String readStringField(DataInputStream dis, int length) throws IOException {
    if (0 == length) {
      return null;
    }
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int b = dis.readUnsignedByte();
      if (0x0 == b) {
        break;
      }
      sb.append((char) b);
    }
    return sb.toString();
  }

  private static class Record {

    private byte valid;
    private String name;
    private String location;
    private String specialities;
    private String numberOfWorkers;
    private String rate;
    private String owner;

    public void setValid(byte valid) {
      this.valid = valid;
    }

    public byte getValid() {
      return valid;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public String getLocation() {
      return location;
    }

    public void setLocation(String location) {
      this.location = location;
    }

    public String getSpecialities() {
      return specialities;
    }

    public void setSpecialities(String specialities) {
      this.specialities = specialities;
    }

    public String getNumberOfWorkers() {
      return numberOfWorkers;
    }

    public void setNumberOfWorkers(String numberOfWorkers) {
      this.numberOfWorkers = numberOfWorkers;
    }

    public String getRate() {
      return rate;
    }

    public void setRate(String rate) {
      this.rate = rate;
    }

    public String getOwner() {
      return owner;
    }

    public void setOwner(String owner) {
      this.owner = owner;
    }
  }
}