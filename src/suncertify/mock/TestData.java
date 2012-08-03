package suncertify.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import suncertify.db.DBAccessImpl;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

public class TestData {
  private static final Logger log = LoggerFactory.getLogger(TestData.class);

  private DBAccessImpl database = DBAccessImpl.getInstance();

  public String[] getTestRecord() {
    return new String[] { "some name", "some location", "some specialties", "" + (int) (Math.random() * 100), "$" + (int) (Math.random() * 10) + ".00", "0" };
  }

  public void deleteRecord(long location) {
    try {
      log.info("Delete record: {}", location);
      database.deleteRecord(location, 0);
    } catch (SecurityException e) {
      log.info(e.getMessage(), e);
    } catch (RecordNotFoundException e) {
      log.info(e.getMessage(), e);
    }
  }

  public void updateRecord(long location, String[] data) {
    try {
      log.info("Update record: {}", location);
      database.updateRecord(location, data, 0);
    } catch (SecurityException e) {
      log.info(e.getMessage(), e);
    } catch (RecordNotFoundException e) {
      log.info(e.getMessage(), e);
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
