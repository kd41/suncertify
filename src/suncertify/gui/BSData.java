package suncertify.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import suncertify.db.DBAccessLocalImpl;
import suncertify.db.RecordNotFoundException;

public class BSData {
  protected static final long COOKIE;
  private Set<Long> cookies;

  private String[] criteria;

  static {
    COOKIE = System.currentTimeMillis();
  }

  private DBAccessLocalImpl data = DBAccessLocalImpl.getInstance();

  public void setCookies(Set<Long> cookies) {
    this.cookies = cookies;
  }

  public Set<Long> getCookies() {
    return cookies;
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
        row.setCookie(dbRow[8]);
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

  protected boolean deleteRecord(long recNo) {
    try {
      data.deleteRecord(recNo, COOKIE);
    } catch (Exception e) {
      System.out.println("error: " + e.getMessage());
      return false;
    }
    return true;
  }

  protected boolean createRecord(String[] recordData) {
    try {
      return data.createRecord(recordData) != 0;
    } catch (Exception e) {
      System.out.println("error: " + e.getMessage());
      return false;
    }
  }

  protected long lockRow(long recNo) {
    try {
      return data.lockRecord(recNo);
    } catch (Exception e) {
      System.out.println("error: " + e.getMessage());
      return 0;
    }
  }

  protected boolean unlockRow(long recNo, long cookie) {
    try {
      data.unlock(recNo, cookie);
    } catch (Exception e) {
      System.out.println("error: " + e.getMessage());
      return false;
    }
    return true;
  }

  protected boolean updateRow(long recNo, String[] data, long lockCookie) {
    try {
      this.data.updateRecord(recNo, data, lockCookie);
    } catch (Exception e) {
      System.out.println("error: " + e.getMessage());
      return false;
    }
    return true;
  }

  public void setCriteria(String[] criteria) {
    this.criteria = criteria;
  }

  public String[] getCriteria() {
    if (criteria == null) {
      return new String[] { null, null, null, null, null, null };
    }
    return criteria;
  }
}
