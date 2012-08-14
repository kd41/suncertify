package suncertify.gui;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BSJFrame extends BSJFrameBase {

  public static void main(String... args) throws IOException {
    // TODO: main method is only for test
    new BSJFrame();
  }

  public BSJFrame() {
    super();
    refreshBtn.addActionListener(new BSActionListener(table, BSDataType.REFRESH));
    searchBtn.addActionListener(new BSActionListener(table, BSDataType.SEARCH,
                                                     new JTextField[] { nameField, locationField, specialitiesField,
                                                                       workersNrField, rateField, ownerField }) {
    });
    createBtn.addActionListener(new BSActionListener(table, BSDataType.CREATE,
                                                     new JTextField[] { nameField, locationField, specialitiesField,
                                                                       workersNrField, rateField, ownerField }) {
    });
    deleteBtn.addActionListener(new BSActionListener(table, BSDataType.DELETE));
    lockBtn.addActionListener(new BSActionListener(table, BSDataType.LOCK));
    unlockBtn.addActionListener(new BSActionListener(table, BSDataType.UNLOCK));
    setColums(table);
  }

  public class BSActionListener implements ActionListener {
    private JTable table;
    private BSDataType type;
    private JTextField[] data;

    public BSActionListener(JTable table, BSDataType type) {
      this.table = table;
      this.type = type;
    }

    public BSActionListener(JTable table, BSDataType type, JTextField[] data) {
      this(table, type);
      this.data = data;
    }

    public void setData(JTextField[] data) {
      this.data = data;
    }

    public JTextField[] getData() {
      return data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      int rowSelected = table.getSelectedRow();
      if (type == BSDataType.REFRESH) {
        setCriteria(null);
      } else if (type == BSDataType.DELETE) {
        if (rowSelected >= 0) {
          int recNo = Integer.parseInt((String) table.getModel().getValueAt(rowSelected, 1));
          boolean isDeleted = deleteRecord(recNo);
          // TODO: need some notification for user?
        }
      } else if (type == BSDataType.CREATE) {
        createRecord(new String[] { data[0].getText(), data[1].getText(), data[2].getText(), data[3].getText(),
                                   data[4].getText(), data[5].getText() });
      } else if (type == BSDataType.SEARCH) {
        setCriteria(new String[] { data[0].getText(), data[1].getText(), data[2].getText(), data[3].getText(),
                                  data[4].getText(), data[5].getText() });
      } else if (type == BSDataType.LOCK) {
        if (rowSelected >= 0) {
          int recNo = Integer.parseInt((String) table.getModel().getValueAt(rowSelected, 1));
          long cookie = lockRow(recNo);
        }

      } else if (type == BSDataType.UNLOCK) {
        if (rowSelected >= 0) {
          int recNo = Integer.parseInt((String) table.getModel().getValueAt(rowSelected, 1));
          long cookie = Long.parseLong((String) table.getModel().getValueAt(rowSelected, 9));
          boolean isUnlocked = unlockRow(recNo, cookie);
        }
      }

      String[][] dataTable = findByCriteria();
      table.setModel(new DefaultTableModel(dataTable, BSJRow.getHeaders()));
      setColums(table);
    }

  }

}
