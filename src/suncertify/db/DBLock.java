package suncertify.db;

public class DBLock {
  private boolean isLockedDB = false;

  public synchronized void lockDB() throws InterruptedException {
    while (isLockedDB) {
      wait();
    }
    isLockedDB = true;
  }

  public synchronized void unlockDB() {
    isLockedDB = false;
    notify();
  }
}
