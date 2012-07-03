package suncertify.parser;

import suncertify.constants.StringPool;

public class DBRecordHelper {

  public static String[] getStringArray(DBRecord record) {
    return new String[] { record.getValid(), record.getName(), record.getLocation(), record.getSpecialties(), record.getNumberOfWorkers(), record.getRate(), record.getOwner() };
  }

  public static void addRecord(String[] data) {
    DBRecord record = getDBRecordFromStringArray(data);
    record.setPosition(DBPresenter.getInstance().getRecords().size() + 1);
    DBPresenter.getInstance().getRecords().add(record);
  }

  public static DBRecord getDBRecordFromStringArray(String[] data) {
    DBRecord record = new DBRecord();
    if (data.length == 6) {
      record.setValid(StringPool.BLANK);
      record.setName(data[0]);
      record.setLocation(data[1]);
      record.setSpecialties(data[2]);
      record.setNumberOfWorkers(data[3]);
      record.setRate(data[4]);
      record.setOwner(data[5]);
    }
    return record;
  }
}