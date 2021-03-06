package suncertify.db;

/**
 * The data access class.
 */
public class Data implements DBAccess {
  private boolean isLockedDB = false;

  private static Data dbAccessLocalImpl;

  private Data() {
  }

  /**
   * Gets the single instance of Data.
   * 
   * @return single instance of Data
   */
  public static synchronized Data getInstance() {
    if (dbAccessLocalImpl == null) {
      dbAccessLocalImpl = new Data();
    }
    return dbAccessLocalImpl;
  }

  @Override
  public String[] readRecord(long recNo) throws RecordNotFoundException {
    try {
      lock();
      return DBAccessImpl.getInstance().readRecord(recNo);
    } finally {
      unlock();
    }
  }

  @Override
  public void updateRecord(long recNo, String[] data, long lockCookie) throws RecordNotFoundException,
                                                                      SecurityException {
    try {
      lock();
      DBAccessImpl.getInstance().updateRecord(recNo, data, lockCookie);
    } finally {
      unlock();
    }
  }

  @Override
  public void deleteRecord(long recNo, long lockCookie) throws RecordNotFoundException, SecurityException {
    try {
      lock();
      DBAccessImpl.getInstance().deleteRecord(recNo, lockCookie);
    } finally {
      unlock();
    }
  }

  @Override
  public long[] findByCriteria(String[] criteria) {
    try {
      lock();
      return DBAccessImpl.getInstance().findByCriteria(criteria);
    } finally {
      unlock();
    }
  }

  @Override
  public long createRecord(String[] data) throws DuplicateKeyException {
    try {
      lock();
      return DBAccessImpl.getInstance().createRecord(data);
    } finally {
      unlock();
    }
  }

  @Override
  public long lockRecord(long recNo) throws RecordNotFoundException {
    return DBAccessImpl.getInstance().lockRecord(recNo);
  }

  @Override
  public void unlock(long recNo, long cookie) throws SecurityException {
    DBAccessImpl.getInstance().unlock(recNo, cookie);
  }

  private synchronized void lock() {
    while (isLockedDB) {
      try {
        wait();
      } catch (InterruptedException e) {
      }
    }
    isLockedDB = true;
  }

  private synchronized void unlock() {
    isLockedDB = false;
    notify();
  }

}
