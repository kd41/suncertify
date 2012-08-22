package suncertify.db;

public class Data implements DBAccess {
  private boolean isLockedDB = false;
  private boolean isLockLocked = false;

  private static Data dbAccessLocalImpl;

  private Data() {
  }

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
    lockLock();
    long cookie = DBAccessImpl.getInstance().lockRecord(recNo);
    return cookie;
  }

  @Override
  public void unlock(long recNo, long cookie) throws SecurityException {
    DBAccessImpl.getInstance().unlock(recNo, cookie);
    unlockLock();
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

  private synchronized void lockLock() {
    while (isLockLocked) {
      try {
        wait();
      } catch (InterruptedException e) {
      }
    }
    isLockLocked = true;
  }

  private synchronized void unlockLock() {
    isLockLocked = false;
    notify();
  }

}
