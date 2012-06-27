package suncertify.parser;

import static java.lang.System.out;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbReader {
  private static final Logger log = LoggerFactory.getLogger(DbReader.class);

  public static void main(String... args) throws IOException {
    File file = new File("C:\\projects\\sun\\suncertify\\db-2x3-ext.db");
    FileInputStream fis = new FileInputStream(file);
    BufferedInputStream bis = new BufferedInputStream(fis);
    DataInputStream dis = new DataInputStream(bis);
    out.printf("---\nHeader\n---\n");
    out.printf("Magic cookie: %d\n", dis.readInt());
    int fieldNum = dis.readUnsignedShort();
    out.printf("Number of fields in each record: %d\n", fieldNum);
    out.printf("---\nSchema\n---\n");
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < fieldNum; i++) {
      int recordNameLength = dis.readUnsignedByte();
      for (int j = 0; j < recordNameLength; j++) {
        sb.append((char) dis.readUnsignedByte());
      }
      out.printf("Field name: %s\tField length: %d\n", sb.toString(), dis.readUnsignedByte());
      sb.setLength(0);
    }
    out.printf("---\nData\n---\n");
    try {
      while (true) {
        log.info("qqqq");
        Record r = readNextRecord(dis);
        out.printf("%b\t%s\t%s\t%s\t%d\t%s\t%d\n", r.isValid(), r.getName(), r.getLocation(), r.getSpecialities(), r.getSize(), r.getRate(),
                   r.getOwner());
      }
    } catch (EOFException e) {
    }

    dis.close();
    bis.close();
    fis.close();
  }

  private static Record readNextRecord(DataInputStream dis) throws IOException {
    Record record = new Record();
    record.setValid(!dis.readBoolean());
    record.setName(readStringField(dis, 32));
    record.setLocation(readStringField(dis, 64));
    record.setSpecialities(readStringField(dis, 64));
    record.setSize(readIntField(dis, 6));
    record.setRate(readStringField(dis, 8));
    record.setOwner(readIntField(dis, 8));
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

  private static Integer readIntField(DataInputStream dis, int length) throws IOException {
    String strValue = readStringField(dis, length);
    if (null == strValue || "".equals(strValue.trim())) {
      return 0;
    }
    return Integer.valueOf(strValue.trim());
  }

  private static class Record {

    private boolean valid;
    private String name;
    private String location;
    private String specialities;
    private int size;
    private String rate;
    private int owner;

    public void setValid(boolean valid) {
      this.valid = valid;
    }

    public boolean isValid() {
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

    public int getSize() {
      return size;
    }

    public void setSize(int size) {
      this.size = size;
    }

    public String getRate() {
      return rate;
    }

    public void setRate(String rate) {
      this.rate = rate;
    }

    public int getOwner() {
      return owner;
    }

    public void setOwner(int owner) {
      this.owner = owner;
    }
  }
}