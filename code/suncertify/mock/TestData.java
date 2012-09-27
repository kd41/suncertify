package suncertify.mock;

import suncertify.db.DBAccessImpl;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

public class TestData {

  private DBAccessImpl database = DBAccessImpl.getInstance();

  private static int MAX_RANDOM = 10000;

  public static String[] getTestRecord() {
    return new String[] { "some name" + (int) (Math.random() * MAX_RANDOM),
                         "some location" + (int) (Math.random() * MAX_RANDOM),
                         "some specialties" + (int) (Math.random() * MAX_RANDOM),
                         "" + (int) (Math.random() * MAX_RANDOM), "$" + (int) (Math.random() * MAX_RANDOM) + ".00",
                         "" + (int) (Math.random() * MAX_RANDOM) };
  }

  public void deleteRecord(long location) {
    try {
      System.out.println("Delete record: " + location);
      database.deleteRecord(location, 0);
    } catch (SecurityException e) {
      System.out.println(e);
    } catch (RecordNotFoundException e) {
      System.out.println(e);
    }
  }

  public void updateRecord(long location, String[] data) {
    try {
      System.out.println("Update record: " + location);
      database.updateRecord(location, data, 0);
    } catch (SecurityException e) {
      System.out.println(e);
    } catch (RecordNotFoundException e) {
      System.out.println(e);
    }
  }

  public long[] findByCriteria(String[] criteria) {
    return database.findByCriteria(criteria);
  }

  public String[] readRecord(long recNo) throws RecordNotFoundException {
    return database.readRecord(recNo);
  }

  public long createRecord(String[] data) throws DuplicateKeyException {
    return database.createRecord(data);
  }

}
