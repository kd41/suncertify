package suncertify.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;

import suncertify.db.RecordNotFoundException;

import suncertify.db.DBAccessImpl;

import suncertify.constants.FileUtils;
import suncertify.constants.Variables;
import suncertify.parser.DBPresenter;

public class TestData {
  private static final Logger log = LoggerFactory.getLogger(TestData.class);

  private DBAccessImpl database = new DBAccessImpl();

  public String[] getTestRecord() {
    return new String[] { "some name", "some location", "some specialties", "" + (int) (Math.random() * 100), "$" + (int) (Math.random() * 10) + ".00", "0" };
  }

  public void deleteRecord(long location) {
    try {
      log.info("Delete record: {}", location);
      database.deleteRecord(location, 0);
    } catch (SecurityException e) {
      log.info(e.getMessage(), e);
    } catch (RecordNotFoundException e) {
      log.info(e.getMessage(), e);
    }
  }

  public void updateRecord(long location, String[] data) {
    try {
      log.info("Update record: {}", location);
      database.updateRecord(location, data, 0);
    } catch (SecurityException e) {
      log.info(e.getMessage(), e);
    } catch (RecordNotFoundException e) {
      log.info(e.getMessage(), e);
    }
  }

  public void writeTestData() {
    try {
      log.info("try to write");
      for (int i = 0; i < 5; i++) {
        String[] testData = getTestRecord();
        long position = database.createRecord(testData);
        log.info("Added new record: {}, this position is: {}", DBPresenter.getInstance().getRecords().get(DBPresenter.getInstance().getRecords().size() - 1), position);
      }
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
  }

  public void testReplace() {
    FileUtils.copyFile(Variables.getFullFilePathTemp2(), Variables.getFullFilePathTemp3());
    replaceInFile(Variables.getFullFilePathTemp3(), "ZXCcccc", 30, 5);
  }

  public boolean replaceInFile(String path, String newText, int srcPos, int len) {
    log.info("Done");
    try {
      log.info("relpace in " + path + " '" + newText + "' from  " + srcPos + " with len " + len);
      byte[] newb = newText.getBytes();
      RandomAccessFile rfile = new RandomAccessFile(path, "rw");
      int flen = (int) rfile.length();
      byte[] buf = new byte[Math.max(flen, flen + newb.length - len)];
      rfile.readFully(buf, 0, flen);
      System.arraycopy(newb, 0, buf, srcPos, len);
      rfile.seek(0);
      rfile.write(buf, 0, flen + newb.length - len);
      rfile.setLength(flen + newb.length - len);
      int newlen = (int) rfile.length();
      rfile.close();
      log.info("Done old len " + flen + " new len " + newlen);
    } catch (IOException e) {
      log.info(e.getMessage(), e);
      return false;
    }
    return true;
  }
}
