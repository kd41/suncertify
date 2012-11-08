package suncertify.mock;

import java.util.Date;

import suncertify.db.RecordNotFoundException;
import suncertify.gui.BSJFrame;
import suncertify.program.Mode;

public class StandaloneTest extends BSJFrame {
  final static StandaloneTest standalone = new StandaloneTest(Mode.STANDALONE);

  public StandaloneTest(Mode mode) {
    super(mode);
  }

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      runTest();
    }

    Thread t5 = new Thread(new Runnable() {
      @Override
      public void run() {
        final String name = Thread.currentThread().getName();
        do {
          int count = standalone.table.getModel().getRowCount();
          System.out.printf("\n%s: rows count= %d", name, count);

          System.out.printf("\n" + new Date());
          sleep(1000, name);
        } while (true);
      }
    }, "show max visible count");
    t5.start();
  }

  private static void runTest() {
    standalone.setCriteria(new String[] { "", "", "", "", "", "" });

    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        final String name = Thread.currentThread().getName();
        do {
          long start = System.currentTimeMillis();
          standalone.findByCriteria();
          long end = System.currentTimeMillis();
          System.out.printf("\n%s: %d", name, (end - start));
          sleep(1000, name);
        } while (true);

      }
    }, "findByCriteria");
    t1.start();

    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        final String name = Thread.currentThread().getName();
        do {
          standalone.createRecord(TestData.getTestRecord());
          System.out.printf("\n create record");
          sleep(10, name);
        } while (true);
      }
    }, "create");
    t2.start();

    Thread t3 = new Thread(new Runnable() {
      @Override
      public void run() {
        final String name = Thread.currentThread().getName();
        do {
          int recNo = (int) (Math.random() * standalone.table.getModel().getRowCount());
          long lockCookie = -1;
          try {
            long start = System.currentTimeMillis();
            lockCookie = standalone.lockRow(recNo);
            long end = System.currentTimeMillis();
            System.out.printf("\n%s lock time: %d + cookie: %d", name, (end - start), lockCookie);

            start = System.currentTimeMillis();
            standalone.updateRow(recNo, TestData.getTestRecord(), lockCookie);
            end = System.currentTimeMillis();
            System.out.printf("\n%s update time: %d + cookie: %d", name, (end - start), lockCookie);

            start = System.currentTimeMillis();
            standalone.unlockRow(recNo, lockCookie);
            end = System.currentTimeMillis();
            System.out.printf("\n%s unlock time: %d + cookie: %d", name, (end - start), lockCookie);
          } catch (RecordNotFoundException e) {
            System.out.printf("\nError. RecordNotFoundException [%d] in speeping thread: %s", recNo, name);
          } catch (SecurityException e) {
            System.out.printf("\nError. SecurityException [%d] in speeping thread: %s, cookie: %d", recNo, name,
                              lockCookie);
          }
          sleep(50, name);
        } while (true);
      }
    }, "update");
    t3.start();

    Thread t4 = new Thread(new Runnable() {
      @Override
      public void run() {
        final String name = Thread.currentThread().getName();
        do {
          int recNo = (int) (Math.random() * standalone.table.getModel().getRowCount());
          long lockCookie = -1;
          try {
            long start = System.currentTimeMillis();
            lockCookie = standalone.lockRow(recNo);
            long end = System.currentTimeMillis();
            System.out.printf("\n%s lock time: %d + coockie: %d", name, (end - start), lockCookie);

            start = System.currentTimeMillis();
            standalone.deleteRecord(recNo, lockCookie);
            end = System.currentTimeMillis();
            System.out.printf("\n%s delete time: %d + cookie: %d", name, (end - start), lockCookie);

            start = System.currentTimeMillis();
            standalone.unlockRow(recNo, lockCookie);
            end = System.currentTimeMillis();
            System.out.printf("\n%s unlock time: %d + cookie: %d", name, (end - start), lockCookie);
          } catch (RecordNotFoundException e) {
            System.out.printf("\nError. RecordNotFoundException [%d] in speeping thread: %s", recNo, name);
          } catch (SecurityException e) {
            System.out.printf("\nError. SecurityException [%d] in speeping thread: %s, cookie: %d", recNo, name,
                              lockCookie);
          }
          sleep(10, name);
        } while (true);
      }
    }, "delete");
    t4.start();

  }

  private static void sleep(int time, String name) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      System.out.printf("\nError in speeping thread: %s", name);
    }
  }
}