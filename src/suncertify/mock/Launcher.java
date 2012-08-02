package suncertify.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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

    testData.writeTestData();
    // log.info("{}", presenter);

    // testData.testReplace();

    testData.deleteRecord(33);
    testData.deleteRecord(32);

    DBRecord newRecord;
    try {
      newRecord = presenter.getRecord(22);
      newRecord.setName(newRecord.getName().substring(0, 15) + " this added!!!!!");
      testData.updateRecord(22, DBRecordHelper.getDBRecordAsStringArray2(newRecord));
    } catch (RecordNotFoundException e) {
      log.error(e.getMessage(), e);
    }

    log.info("{}", presenter);
  }
}
