package suncertify.db;

public class DBAccessLocalImpl implements DBAccess {
  private boolean isLockedDB = false;

  private static DBAccessLocalImpl dbAccessLocalImpl;

  private DBAccessLocalImpl() {
  }

  public static synchronized DBAccessLocalImpl getInstance() {
    if (dbAccessLocalImpl == null) {
      dbAccessLocalImpl = new DBAccessLocalImpl();
    }
    return dbAccessLocalImpl;
  }

  @Override
  public String[] readRecord(long recNo) throws RecordNotFoundException {
    if (isLock()) {
      try {
        return DBAccessImpl.getInstance().readRecord(recNo);
      } finally {
        unlock();
      }
    }
    return null;
  }

  @Override
  public void updateRecord(long recNo, String[] data, long lockCookie) throws RecordNotFoundException, SecurityException {
    if (isLock()) {
      try {
        DBAccessImpl.getInstance().updateRecord(recNo, data, lockCookie);
      } finally {
        unlock();
      }
    }
  }

  @Override
  public void deleteRecord(long recNo, long lockCookie) throws RecordNotFoundException, SecurityException {
    if (isLock()) {
      try {
        DBAccessImpl.getInstance().deleteRecord(recNo, lockCookie);
      } finally {
        unlock();
      }
    }
  }

  @Override
  public long[] findByCriteria(String[] criteria) {
    if (isLock()) {
      try {
        return DBAccessImpl.getInstance().findByCriteria(criteria);
      } finally {
        unlock();
      }
    }
    return null;
  }

  @Override
  public long createRecord(String[] data) throws DuplicateKeyException {
    if (isLock()) {
      try {
        return DBAccessImpl.getInstance().createRecord(data);
      } finally {
        unlock();
      }
    }
    return 0;
  }

  @Override
  public long lockRecord(long recNo) throws RecordNotFoundException {
    if (isLock()) {
      try {
        return DBAccessImpl.getInstance().lockRecord(recNo);
      } finally {
        unlock();
      }
    }
    return 0;
  }

  @Override
  public void unlock(long recNo, long cookie) throws SecurityException {
    if (isLock()) {
      try {
        DBAccessImpl.getInstance().unlock(recNo, cookie);
      } finally {
        unlock();
      }
    }
  }

  private synchronized boolean isLock() {
    try {
      while (isLockedDB) {
        wait();
      }
    } catch (InterruptedException e) {
      return false;
    }
    return true;
  }

  private synchronized void unlock() {
    notify();
  }

}
