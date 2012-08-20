package suncertify.gui;

import java.util.ArrayList;
import java.util.List;

import suncertify.db.DBAccessLocalImpl;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

public class BSData {
  private String[] criteria;
  private int selectedRow;

  private DBAccessLocalImpl data = DBAccessLocalImpl.getInstance();

  public void setCriteria(String[] criteria) {
    this.criteria = criteria;
  }

  public String[] getCriteria() {
    if (criteria == null) {
      return new String[] { null, null, null, null, null, null };
    }
    return criteria;
  }

  public void setSelectedRow(int selectedRow) {
    this.selectedRow = selectedRow;
  }

  public int getSelectedRow() {
    return selectedRow;
  }

  protected String[][] findByCriteria() {
    long[] records = data.findByCriteria(getCriteria());
    List<BSJRow> rows = new ArrayList<BSJRow>();
    int number = 1;
    for (long record : records) {
      try {
        String[] dbRow = data.readRecord(record);
        BSJRow row = new BSJRow();
        row.setNumber(number++);
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

  protected void deleteRecord(long recNo, long cookie) throws RecordNotFoundException, SecurityException {
    data.deleteRecord(recNo, cookie);
  }

  protected boolean createRecord(String[] recordData) {
    try {
      return data.createRecord(recordData) != 0;
    } catch (DuplicateKeyException e) {
      return false;
    }
  }

  protected long lockRow(long recNo) {
    try {
      return data.lockRecord(recNo);
    } catch (RecordNotFoundException e) {
      return 0;
    }
  }

  protected boolean unlockRow(long recNo, long cookie) {
    try {
      data.unlock(recNo, cookie);
    } catch (SecurityException e) {
      return false;
    }
    return true;
  }

  protected void updateRow(long recNo, String[] data, long lockCookie) throws RecordNotFoundException,
                                                                      SecurityException {
    this.data.updateRecord(recNo, data, lockCookie);
  }

  protected enum ErrorType {
    RECORD_NOT_FOUND, SECURITY, DUPLICATE_KEY
  }
}
