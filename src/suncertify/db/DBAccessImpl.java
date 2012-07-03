package suncertify.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import suncertify.parser.DBPresenter;
import suncertify.parser.DBReaderWriter;
import suncertify.parser.DBRecordHelper;

public class DBAccessImpl implements DBAccess {
  private static final Logger log = LoggerFactory.getLogger(DBAccessImpl.class);

  @Override
  public String[] readRecord(long recNo) throws RecordNotFoundException {
    // TODO Auto-generated method stub
    return null;
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
    if (presenter.getRecords().contains(DBRecordHelper.getDBRecordFromStringArray(data))) {
      throw new DuplicateKeyException();
    }
    try {
      DBReaderWriter.writeRecord(data);
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    return DBPresenter.getInstance().getRecords().size();
  }

  @Override
  public long lockRecord(long recNo) throws RecordNotFoundException {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void unlock(long recNo, long cookie) throws SecurityException {
    // TODO Auto-generated method stub

  }

}
