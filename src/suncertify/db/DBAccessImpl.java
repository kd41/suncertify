package suncertify.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import suncertify.parser.DBRecord;

import suncertify.parser.DBPresenter;
import suncertify.parser.DBReaderWriter;
import suncertify.parser.DBRecordHelper;

public class DBAccessImpl implements DBAccess {
  private static final Logger log = LoggerFactory.getLogger(DBAccessImpl.class);

  @Override
  public String[] readRecord(long recNo) throws RecordNotFoundException {
    DBPresenter presenter = DBPresenter.getInstance();
    DBRecord record = presenter.getRecord(recNo);
    return DBRecordHelper.getDBRecordAsStringArray(record);
  }

  @Override
  public void updateRecord(long recNo, String[] data, long lockCookie) throws RecordNotFoundException, SecurityException {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteRecord(long recNo, long lockCookie) throws RecordNotFoundException, SecurityException {
    // TODO Auto-generated method stub

  }

  @Override
  public long[] findByCriteria(String[] criteria) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public long createRecord(String[] data) throws DuplicateKeyException {
    DBPresenter presenter = DBPresenter.getInstance();
    DBRecord record = DBRecordHelper.createDBRecord(data);
    if (presenter.getRecords().contains(record)) {
      throw new DuplicateKeyException("Database contains: " + record);
    }
    try {
      DBReaderWriter.addRecord(data);
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    return record.getPosition();
  }

  @Override
  public long lockRecord(long recNo) throws RecordNotFoundException {
    DBPresenter presenter = DBPresenter.getInstance();
    DBRecord record = presenter.getRecord(recNo);
    if (!record.isLocked()) {
      long cookie = System.currentTimeMillis();
      record.setCookie(cookie);
      return cookie;
    }
    return 0;
  }

  @Override
  public void unlock(long recNo, long cookie) throws SecurityException {
    DBPresenter presenter = DBPresenter.getInstance();
    DBRecord record = null;
    try {
      record = presenter.getRecord(recNo);
    } catch (RecordNotFoundException e) {
      log.error(e.getMessage(), e);
      return;
    }
    if (record.isLocked() && record.getCookie() != cookie) {
      throw new SecurityException("The record " + recNo + " is locked with cookie: " + record.getCookie() + ". You cant' unlock this record with cookie: " + cookie);
    }
    record.setCookie(0);
  }
}
