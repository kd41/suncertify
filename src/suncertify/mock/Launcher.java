package suncertify.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import suncertify.parser.DBRecordHelper;

import suncertify.constants.FileUtils;
import suncertify.constants.Variables;
import suncertify.db.DBAccessImpl;
import suncertify.parser.DBPresenter;
import suncertify.parser.DBReaderWriter;

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
  }

}
