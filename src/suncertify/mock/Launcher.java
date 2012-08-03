package suncertify.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

import suncertify.constants.FileUtils;
import suncertify.constants.Variables;
import suncertify.db.RecordNotFoundException;
import suncertify.parser.DBPresenter;
import suncertify.parser.DBReaderWriter;
import suncertify.parser.DBRecord;
import suncertify.parser.DBRecordHelper;

public class Launcher {
  private static final Logger log = LoggerFactory.getLogger(Launcher.class);

  public static void main(String... args) throws IOException {
    // main method is only for test!!
    log.info("START");
    FileUtils.copyFile(Variables.getOriginalFilePath(), Variables.getWorkedFilePath());
    TestData testData = new TestData();

    DBPresenter presenter = DBReaderWriter.createDatabasePresenter();
    log.info("{}", presenter);

    // create records
    try {
      log.info("try to write");
      for (int i = 0; i < 5; i++) {
        String[] testRecord = testData.getTestRecord();
        long position = testData.createRecord(testRecord);
        log.info("Added new record: {}, this position is: {}", DBPresenter.getInstance().getRecords().get(DBPresenter.getInstance().getRecords().size() - 1), position);
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
  }
}
