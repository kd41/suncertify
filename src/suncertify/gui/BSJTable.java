package suncertify.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import suncertify.db.DBAccessLocalImpl;
import suncertify.db.RecordNotFoundException;
import suncertify.parser.DBPresenter;
import suncertify.parser.DBReaderWriter;

public class BSJTable {

  DBAccessLocalImpl data = DBAccessLocalImpl.getInstance();

  public static void main(String... args) throws IOException {
    // TODO: main method is only for test
    DBPresenter presenter = DBReaderWriter.createDatabasePresenter();
    BSJTable frame = new BSJTable();
  }

  public BSJTable() {
    JFrame frame = new JFrame("Bodgitt and Scarper, LLC");
    JPanel panel = new JPanel();
    String data[][] = findByCriteria(new String[] { null, null, null, null, null, null });
    String[] columnNames = BSJRow.getHeaders();
    JTable table = new JTable(data, columnNames);
    JTableHeader header = table.getTableHeader();
    header.setBackground(Color.yellow);
    JScrollPane pane = new JScrollPane(table);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    panel.add(pane);
    frame.add(panel);
    frame.setSize(800, 600);
    frame.setUndecorated(true);
    frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  private String[][] findByCriteria(String[] criteria) {
    long[] records = data.findByCriteria(criteria);
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
}
