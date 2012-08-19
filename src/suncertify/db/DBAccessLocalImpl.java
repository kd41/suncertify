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
    try {
      lock();
      return DBAccessImpl.getInstance().lockRecord(recNo);
    } finally {
      unlock();
    }
  }

  @Override
  public void unlock(long recNo, long cookie) throws SecurityException {
    try {
      lock();
      DBAccessImpl.getInstance().unlock(recNo, cookie);
    } finally {
      unlock();
    }
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
