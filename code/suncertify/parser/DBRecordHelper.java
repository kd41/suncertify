package suncertify.parser;

/**
 * The Class DBRecordHelper. Helper class to unite database and presentation of database.
 */
public class DBRecordHelper {

  /**
   * Instantiates a new database record helper.
   */
  public DBRecordHelper() {
  }

  /**
   * Gets the database record as string array.
   * 
   * @param record the record
   * @return the database record as string array
   */
  public static String[] getDBRecordAsStringArray(DBRecord record) {
    return new String[] { String.valueOf(record.getPosition()), String.valueOf(record.getValid()), record.getName(),
                         record.getLocation(), record.getSpecialties(), record.getNumberOfWorkers(), record.getRate(),
                         record.getOwner(), String.valueOf(record.getCookie()) };
  }

  /**
   * Gets the database record as string array. Version 2.
   * 
   * @param record the record
   * @return the database record as string array (version 2)
   */
  public static String[] getDBRecordAsStringArray2(DBRecord record) {
    return new String[] { record.getName(), record.getLocation(), record.getSpecialties(), record.getNumberOfWorkers(),
                         record.getRate(), record.getOwner() };
  }

  /**
   * Creates the database record.
   * 
   * @param data the data
   * @return the database record
   */
  public static DBRecord createDBRecord(String[] data) {
    DBRecord record = new DBRecord();
    if (data.length == 6) {
      record.setValid((byte) 0);
      record.setPosition(DBPresenter.getInstance().getRecords().size());
      record.setName(data[0]);
      record.setLocation(data[1]);
      record.setSpecialties(data[2]);
      record.setNumberOfWorkers(data[3]);
      record.setRate(data[4]);
      record.setOwner(data[5]);
    } else {
      throw new RuntimeException("Wrong data to create DBRecord!");
    }
    return record;
  }

}
