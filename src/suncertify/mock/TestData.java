package suncertify.mock;

import suncertify.db.DBAccessImpl;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

public class TestData {

  private DBAccessImpl database = DBAccessImpl.getInstance();

  public String[] getTestRecord() {
    return new String[] { "some name", "some location", "some specialties", "" + (int) (Math.random() * 100),
                         "$" + (int) (Math.random() * 10) + ".00", "0" };
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
