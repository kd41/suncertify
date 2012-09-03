package suncertify.db;

import java.util.ArrayList;
import java.util.List;

import suncertify.parser.DBPresenter;
import suncertify.parser.DBReaderWriter;
import suncertify.parser.DBRecord;
import suncertify.parser.DBRecordHelper;

/**
 * The Class DBAccessImpl.
 */
public class DBAccessImpl {

  private static DBAccessImpl dbAccessImpl;

  private long cookie;

  private DBAccessImpl() {
  }

  /**
   * Gets the single instance of DBAccessImpl.
   * 
   * @return single instance of DBAccessImpl
   */
  public static synchronized DBAccessImpl getInstance() {
    if (dbAccessImpl == null) {
      dbAccessImpl = new DBAccessImpl();
    }
    return dbAccessImpl;
  }

  /**
   * Read record.
   * 
   * @param recNo the number of record
   * @return the string[]
   * @throws RecordNotFoundException the record not found exception
   */
  public String[] readRecord(long recNo) throws RecordNotFoundException {
    validateRecordNumber(recNo);
    DBPresenter presenter = DBPresenter.getInstance();
    DBRecord record = presenter.getRecord(recNo);
    return DBRecordHelper.getDBRecordAsStringArray(record);
  }

  /**
   * Update record.
   * 
   * @param recNo the number of record
   * @param data the data
   * @param lockCookie the lock cookie
   * @throws RecordNotFoundException the record not found exception
   * @throws SecurityException the security exception
   */
  public void updateRecord(long recNo, String[] data, long lockCookie) throws RecordNotFoundException,
                                                                      SecurityException {
    validateRecordNumber(recNo);
    DBPresenter presenter = DBPresenter.getInstance();
    DBRecord record = presenter.getRecord(recNo);
    record.setName(data[0]);
    record.setLocation(data[1]);
    record.setSpecialties(data[2]);
    record.setNumberOfWorkers(data[3]);
    record.setRate(data[4]);
    record.setOwner(data[5]);
    if (record.getCookie() != lockCookie) {
      throw new SecurityException("The record " + recNo + " with cookie " + record.getCookie()
                                  + " can't be updated with cookie " + lockCookie);
    }
    try {
      DBReaderWriter.updateRecord(record);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Delete record.
   * 
   * @param recNo the number of record
   * @param lockCookie the lock cookie
   * @throws RecordNotFoundException the record not found exception
   * @throws SecurityException the security exception
   */
  public void deleteRecord(long recNo, long lockCookie) throws RecordNotFoundException, SecurityException {
    validateRecordNumber(recNo);
    DBPresenter presenter = DBPresenter.getInstance();
    DBRecord record = presenter.getRecord(recNo);
    if (record.getCookie() != lockCookie) {
      throw new SecurityException("The record " + recNo + " with cookie " + record.getCookie()
                                  + " can't be deleted with cookie " + lockCookie);
    }
    try {
      DBReaderWriter.deleteRecord(record);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Find by criteria.
   * 
   * @param criteria the criteria
   * @return the long[]
   */
  public long[] findByCriteria(String[] criteria) {
    if (criteria == null || criteria.length != 6) {
      return null;
    }
    DBPresenter presenter = DBPresenter.getInstance();
    List<DBRecord> findedRecords = new ArrayList<DBRecord>();
    for (DBRecord record : presenter.getRecords()) {
      if (!record.isValid()) {
        continue;
      }
      String[] recordArray = DBRecordHelper.getDBRecordAsStringArray2(record);
      boolean isMached = true;
      for (int i = 0; i < recordArray.length; i++) {
        if (criteria[i] != null && !recordArray[i].startsWith(criteria[i])) {
          isMached = false;
          break;
        }
      }
      if (isMached) {
        findedRecords.add(record);
      }
    }
    long[] findedNumbers = new long[findedRecords.size()];
    int count = 0;
    for (DBRecord record : findedRecords) {
      findedNumbers[count++] = record.getPosition();
    }
    return findedNumbers;
  }

  /**
   * Creates the record.
   * 
   * @param data the data
   * @return the long
   * @throws DuplicateKeyException the duplicate key exception
   */
  public long createRecord(String[] data) throws DuplicateKeyException {
    DBPresenter presenter = DBPresenter.getInstance();
    DBRecord record = DBRecordHelper.createDBRecord(data);
    if (presenter.getRecords().contains(record)) {
      throw new DuplicateKeyException("Database contains: " + record);
    }
    try {
      DBReaderWriter.addRecord(data);
    } catch (Exception e) {
      System.out.println(e);
    }
    return record.getPosition();
  }

  /**
   * Lock record.
   * 
   * @param recNo the number of record
   * @return the long
   * @throws RecordNotFoundException the record not found exception
   */
  public long lockRecord(long recNo) throws RecordNotFoundException {
    validateRecordNumber(recNo);
    DBPresenter presenter = DBPresenter.getInstance();
    DBRecord record = presenter.getRecord(recNo);
    record.lockRecord();
    record.setCookie(++cookie);
    return cookie;
  }

  /**
   * Unlock.
   * 
   * @param recNo the number of record
   * @param cookie the cookie
   * @throws SecurityException the security exception
   */
  public void unlock(long recNo, long cookie) throws SecurityException {
    DBPresenter presenter = DBPresenter.getInstance();
    DBRecord record = null;
    try {
      record = presenter.getRecord(recNo, false);
    } catch (RecordNotFoundException e) {
      System.out.println(e);
    }
    if (record.getCookie() != cookie) {
      throw new SecurityException("The record " + recNo + " is locked with cookie: " + record.getCookie()
                                  + ". You cant' unlock this record with cookie: " + cookie);
    }
    record.unlockRecord();
  }

  private void validateRecordNumber(long recNo) throws RecordNotFoundException {
    if (recNo > Integer.MAX_VALUE) {
      throw new RecordNotFoundException("The record number is over than Integer.MAX_VALUE");
    }
  }
}
