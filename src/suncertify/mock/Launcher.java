package suncertify.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import suncertify.parser.DBRecordHelper;

import suncertify.constants.FileUtils;
import suncertify.constants.Variables;
import suncertify.db.DBAccessImpl;
import suncertify.parser.DBPresenter;
import suncertify.parser.DBReaderWriter;

public class Launcher {
  private static final Logger log = LoggerFactory.getLogger(Launcher.class);

  DBAccessImpl database = new DBAccessImpl();

  public static void main(String... args) throws IOException {
    // main method is only for test!!
    Launcher launcher = new Launcher();

    log.info("START");
    FileUtils.copyFile(Variables.getOriginalFilePath(), Variables.getWorkedFilePath());
    DBPresenter presenter = DBReaderWriter.createDatabasePresenter();
    log.info("{}", presenter);

    launcher.writeTestData();
    // log.info("{}", presenter);
  }

  private void writeTestData() {
    try {
      log.info("try to write");
      for (int i = 0; i < 22; i++) {
        String[] testData = TestData.getRecord();
        long position = database.createRecord(testData);
        log.info("Added new record: {}, this position is: {}", DBPresenter.getInstance().getRecords().get(DBPresenter.getInstance().getRecords().size() - 1), position);
      }
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
  }
}
