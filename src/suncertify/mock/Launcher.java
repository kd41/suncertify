package suncertify.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import suncertify.parser.PropertiesLoader;

import suncertify.constants.FileUtils;
import suncertify.constants.Variables;
import suncertify.db.RecordNotFoundException;
import suncertify.gui.BSJFrame;
import suncertify.parser.DBPresenter;
import suncertify.parser.DBRecord;
import suncertify.parser.DBRecordHelper;

public class Launcher {
  private static final Logger log = LoggerFactory.getLogger(Launcher.class);

  public static void main(String... args) {
    // main method is only for test!!
    PropertiesLoader.getInstance().setDBLocation("newdbfile.db");
  }

  private void dothis() throws IOException {
    File f1 = new File(PropertiesLoader.getInstance().getDBLocation());
    File f2 = new File(PropertiesLoader.getInstance().getDBLocation() + "123");
    InputStream in = new FileInputStream(f1);
    OutputStream out = new FileOutputStream(f2);

    byte[] buf = new byte[55];
    int len = in.read(buf);
    out.write(buf, 0, len);

    in.close();
    out.close();
    System.out.println("File copied.");
    log.info("START");
    FileUtils.copyFile(Variables.getOriginalFilePath(), Variables.getWorkedFilePath());
    TestData testData = new TestData();

    DBPresenter presenter = DBPresenter.getInstance();
    log.info("{}", presenter);

    // create records
    try {
      log.info("try to write");
      for (int i = 0; i < 5; i++) {
        String[] testRecord = testData.getTestRecord();
        long position = testData.createRecord(testRecord);
        log.info("Added new record: {}, this position is: {}",
                 DBPresenter.getInstance().getRecords().get(DBPresenter.getInstance().getRecords().size() - 1),
                 position);
      }
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }

    // log.info("{}", presenter);

    // delete records
    testData.deleteRecord(33);
    testData.deleteRecord(32);

    // update records
    DBRecord newRecord;
    try {
      newRecord = presenter.getRecord(22);
      newRecord.setName(newRecord.getName().substring(0, 15) + " this added!!!!!");
      testData.updateRecord(22, DBRecordHelper.getDBRecordAsStringArray2(newRecord));
    } catch (RecordNotFoundException e) {
      log.error(e.getMessage(), e);
    }

    log.info("{}", presenter);

    // find by criteria
    String[] criteria = new String[] { "Bitter", "Sma", null, null, "$7", null };
    long[] records = testData.findByCriteria(criteria);
    log.info("Number of records found: {}", records.length);
    log.info("criteria: {}", Arrays.asList(criteria));
    for (long recNo : records) {
      try {
        log.info("{}", Arrays.asList(testData.readRecord(recNo)));
      } catch (RecordNotFoundException e) {
        log.info("record {} not found!", recNo);
      }
    }

    // gui
    BSJFrame frame = new BSJFrame();
  }
}
