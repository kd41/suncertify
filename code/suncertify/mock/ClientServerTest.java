package suncertify.mock;

import java.util.Date;

import suncertify.db.RecordNotFoundException;
import suncertify.gui.BSJFrame;
import suncertify.program.Mode;

public class ClientServerTest {

  private static int MAX = 1000;

  public static void main(String[] args) {
    runServer();
    sleep(1000, "Wait untill server is started.");
    runFindByCriteriaClient(10000);
    runCreateClient(1000);
    runDeleteClient(2000);
    runUpdateClient(1500);
  }

  static void runCreateClient(final int sleepMilliseconds) {
    final BSJFrame client = new BSJFrame(Mode.NETWORK_CLIENT_AND_GUI);
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        final String name = Thread.currentThread().getName();
        do {
          client.createRecord(TestData.getTestRecord());
          System.out.printf("\n%s create record", name);
          sleep(sleepMilliseconds, name);
        } while (true);
      }
    }, "create client");
    t.start();
  }

  static void runDeleteClient(final int sleepMilliseconds) {
    final BSJFrame client = new BSJFrame(Mode.NETWORK_CLIENT_AND_GUI);
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        final String name = Thread.currentThread().getName();
        do {
          int recNo = (int) (MAX * Math.random());
          long lockCookie = -1;
          try {
            long start = System.currentTimeMillis();
            lockCookie = client.lockRow(recNo);
            long end = System.currentTimeMillis();
            System.out.printf("\n%s lock time: %d + coockie: %d", name, (end - start), lockCookie);

            start = System.currentTimeMillis();
            client.deleteRecord(recNo, lockCookie);
            end = System.currentTimeMillis();
            System.out.printf("\n%s delete time: %d + cookie: %d", name, (end - start), lockCookie);

            start = System.currentTimeMillis();
            client.unlockRow(recNo, lockCookie);
            end = System.currentTimeMillis();
            System.out.printf("\n%s unlock time: %d + cookie: %d", name, (end - start), lockCookie);
          } catch (RecordNotFoundException e) {
            System.out.printf("\nError. RecordNotFoundException [%d] in speeping thread: %s", recNo, name);
          } catch (SecurityException e) {
            System.out.printf("\nError. SecurityException [%d] in speeping thread: %s, cookie: %d", recNo, name,
                              lockCookie);
          }
          sleep(sleepMilliseconds, name);
        } while (true);
      }
    }, "delete client");
    t.start();
  }

  static void runUpdateClient(final int sleepMilliseconds) {
    final BSJFrame client = new BSJFrame(Mode.NETWORK_CLIENT_AND_GUI);
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        final String name = Thread.currentThread().getName();
        do {
          int recNo = (int) (MAX * Math.random());
          long lockCookie = -1;
          try {
            long start = System.currentTimeMillis();
            lockCookie = client.lockRow(recNo);
            long end = System.currentTimeMillis();
            System.out.printf("\n%s lock time: %d + cookie: %d", name, (end - start), lockCookie);

            start = System.currentTimeMillis();
            client.updateRow(recNo, TestData.getTestRecord(), lockCookie);
            end = System.currentTimeMillis();
            System.out.printf("\n%s update time: %d + cookie: %d", name, (end - start), lockCookie);

            start = System.currentTimeMillis();
            client.unlockRow(recNo, lockCookie);
            end = System.currentTimeMillis();
            System.out.printf("\n%s unlock time: %d + cookie: %d", name, (end - start), lockCookie);
          } catch (RecordNotFoundException e) {
            System.out.printf("\nError. RecordNotFoundException [%d] in speeping thread: %s", recNo, name);
          } catch (SecurityException e) {
            System.out.printf("\nError. SecurityException [%d] in speeping thread: %s, cookie: %d", recNo, name,
                              lockCookie);
          }
          sleep(sleepMilliseconds, name);
        } while (true);
      }
    }, "update client");
    t.start();
  }

  static void runFindByCriteriaClient(final int sleepMilliseconds) {
    final BSJFrame client = new BSJFrame(Mode.NETWORK_CLIENT_AND_GUI);
    client.setCriteria(new String[] { "", "", "", "", "", "" });
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        final String name = Thread.currentThread().getName();
        do {
          long start = System.currentTimeMillis();
          client.findByCriteria();
          long end = System.currentTimeMillis();
          System.out.printf("\n%s: %d", name, (end - start));
          MAX = (int) (1.5 * client.table.getModel().getRowCount());
          System.out.printf("\nMAX: %d\n", MAX);
          sleep(sleepMilliseconds, name);
        } while (true);
      }
    }, "find client");
    t.start();
  }

  static void runServer() {
    new BSJFrame(Mode.SERVER);
    System.out.printf("\n" + new Date());
  }

  private static void sleep(int time, String name) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      System.out.printf("\nError in speeping thread: %s", name);
      System.exit(0);
    }
  }
}
