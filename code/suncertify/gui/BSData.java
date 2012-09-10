package suncertify.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import suncertify.db.DBAccess;
import suncertify.db.Data;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;
import suncertify.parser.PropertiesLoader;
import suncertify.program.Mode;
import suncertify.socket.client.DBAccessClientImpl;
import suncertify.socket.server.Server;

/**
 * The Class BSData initializes communication between gui and application data.
 */
public class BSData {
  protected enum ErrorType {
    RECORD_NOT_FOUND, SECURITY, DUPLICATE_KEY
  }

  protected Mode mode;
  protected DBAccess data;
  protected boolean isStarrtedOK = true;

  private String[] criteria;
  private int selectedRow;
  private Thread serverThread;

  protected BSData(Mode mode) {
    this.mode = mode;
    int port = 0;
    try {
      port = Integer.parseInt(PropertiesLoader.getInstance().getDbPort());
    } catch (NumberFormatException e) {
      e.printStackTrace();
      isStarrtedOK = false;
    }
    if (mode == Mode.STANDALONE) {
      data = Data.getInstance();
    } else if (mode == Mode.SERVER) {
      data = Data.getInstance();
      runServer(port);
    } else {
      data = new DBAccessClientImpl(PropertiesLoader.getInstance().getDbHost(), port);
    }
  }

  protected void runServer(final int port) {
    if (serverThread != null) {
      serverThread.interrupt();
      serverThread = null;
    }
    serverThread = new Thread() {
      @Override
      public void run() {
        try {
          new Server(port);
        } catch (IOException e) {
          e.printStackTrace();
          isStarrtedOK = false;
        }
      }
    };
    serverThread.start();
  }

  protected boolean createRecord(String[] recordData) {
    try {
      return data.createRecord(recordData) != 0;
    } catch (DuplicateKeyException e) {
      return false;
    }
  }

  protected void deleteRecord(long recNo, long cookie) throws RecordNotFoundException, SecurityException {
    data.deleteRecord(recNo, cookie);
  }

  protected String[][] findByCriteria() {
    long[] records = data.findByCriteria(getCriteria());
    List<BSJRow> rows = new ArrayList<BSJRow>();
    for (long record : records) {
      try {
        String[] dbRow = data.readRecord(record);
        BSJRow row = new BSJRow();
        row.setPosition(dbRow[0]);
        row.setValid(dbRow[1]);
        row.setName(dbRow[2]);
        row.setLocation(dbRow[3]);
        row.setSpecialties(dbRow[4]);
        row.setNumberOfWorkers(dbRow[5]);
        row.setRate(dbRow[6]);
        row.setOwner(dbRow[7]);
        rows.add(row);
      } catch (RecordNotFoundException e) {
      }
    }
    Collections.sort(rows);
    int number = 1;
    for (BSJRow row : rows) {
      row.setNumber(number++);
    }

    int x = BSJRow.getHeaders().length;
    int y = rows.size();
    String[][] dbData = new String[y][x];
    for (int i = 0; i < y; i++) {
      String[] jRow = rows.get(i).toStringArray();
      for (int j = 0; j < x; j++) {
        dbData[i][j] = jRow[j];
      }
    }
    return dbData;
  }

  protected String[] getCriteria() {
    if (criteria == null) {
      return new String[] { null, null, null, null, null, null };
    }
    return criteria;
  }

  protected int getSelectedRow() {
    return selectedRow;
  }

  protected long lockRow(long recNo) throws RecordNotFoundException {
    return data.lockRecord(recNo);
  }

  protected void setCriteria(String[] criteria) {
    this.criteria = criteria;
  }

  protected void setSelectedRow(int selectedRow) {
    this.selectedRow = selectedRow;
  }

  protected void unlockRow(long recNo, long cookie) throws SecurityException {
    data.unlock(recNo, cookie);
  }

  protected void updateRow(long recNo, String[] data, long lockCookie) throws RecordNotFoundException,
                                                                      SecurityException {
    this.data.updateRecord(recNo, data, lockCookie);
  }
}
