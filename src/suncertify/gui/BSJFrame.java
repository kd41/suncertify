package suncertify.gui;

import static suncertify.gui.BSJRow.COOKIE;
import static suncertify.gui.BSJRow.LOCATION;
import static suncertify.gui.BSJRow.NAME;
import static suncertify.gui.BSJRow.OWNER;
import static suncertify.gui.BSJRow.RATE;
import static suncertify.gui.BSJRow.RECORD_NUMBER;
import static suncertify.gui.BSJRow.SPECIALTIES;
import static suncertify.gui.BSJRow.WORKERS_NUMBER;

import javax.swing.JTable;
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
    searchBtn.addActionListener(new BSActionListener(table, BSDataType.SEARCH));
    createBtn.addActionListener(new BSActionListener(table, BSDataType.CREATE));
    deleteBtn.addActionListener(new BSActionListener(table, BSDataType.DELETE));
    lockBtn.addActionListener(new BSActionListener(table, BSDataType.LOCK));
    unlockBtn.addActionListener(new BSActionListener(table, BSDataType.UNLOCK));
    getBtn.addActionListener(new BSActionListener(table, BSDataType.GET));
    updateBtn.addActionListener(new BSActionListener(table, BSDataType.UPDATE));
    updateBtn.setEnabled(false);
    setColums(table);
  }

  public class BSActionListener implements ActionListener {
    private JTable table;
    private BSDataType type;

    public BSActionListener(JTable table, BSDataType type) {
      this.table = table;
      this.type = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      int rowSelected = table.getSelectedRow();
      if (type == BSDataType.REFRESH) {
        setCriteria(null);
      } else if (type == BSDataType.DELETE) {
        if (rowSelected >= 0) {
          int recNo = Integer.parseInt((String) table.getModel().getValueAt(rowSelected, RECORD_NUMBER));
          long cookie = Long.parseLong((String) table.getModel().getValueAt(rowSelected, COOKIE));
          boolean isDeleted = deleteRecord(recNo, cookie);
          // TODO: need some notification for user?
        }
      } else if (type == BSDataType.CREATE) {
        createRecord(new String[] { nameField.getText(), locationField.getText(), specialitiesField.getText(),
                                   workersNrField.getText(), rateField.getText(), ownerField.getText() });
      } else if (type == BSDataType.SEARCH) {
        setCriteria(new String[] { nameField.getText(), locationField.getText(), specialitiesField.getText(),
                                  workersNrField.getText(), rateField.getText(), ownerField.getText() });
      } else if (type == BSDataType.LOCK) {
        if (rowSelected >= 0) {
          int recNo = Integer.parseInt((String) table.getModel().getValueAt(rowSelected, RECORD_NUMBER));
          long cookie = lockRow(recNo);
        }

      } else if (type == BSDataType.UNLOCK) {
        if (rowSelected >= 0) {
          int recNo = Integer.parseInt((String) table.getModel().getValueAt(rowSelected, RECORD_NUMBER));
          long cookie = Long.parseLong((String) table.getModel().getValueAt(rowSelected, COOKIE));
          boolean isUnlocked = unlockRow(recNo, cookie);
        }
      } else if (type == BSDataType.GET) {
        if (rowSelected >= 0) {
          nameField.setText((String) table.getModel().getValueAt(rowSelected, NAME));
          locationField.setText((String) table.getModel().getValueAt(rowSelected, LOCATION));
          specialitiesField.setText((String) table.getModel().getValueAt(rowSelected, SPECIALTIES));
          workersNrField.setText((String) table.getModel().getValueAt(rowSelected, WORKERS_NUMBER));
          rateField.setText((String) table.getModel().getValueAt(rowSelected, RATE));
          ownerField.setText((String) table.getModel().getValueAt(rowSelected, OWNER));
          updateBtn.setEnabled(true);
          setSelectedRow(rowSelected);
        }
      } else if (type == BSDataType.UPDATE) {
        if (getSelectedRow() > -1) {
          int recNo = Integer.parseInt((String) table.getModel().getValueAt(getSelectedRow(), RECORD_NUMBER));
          long cookie = Long.parseLong((String) table.getModel().getValueAt(getSelectedRow(), COOKIE));
          boolean isUpdated = updateRow(recNo, new String[] { nameField.getText(), locationField.getText(),
                                                             specialitiesField.getText(), workersNrField.getText(),
                                                             rateField.getText(), ownerField.getText() }, cookie);
          if (isUpdated) {
            rowSelected = table.getModel().getRowCount() - 1;
          }
        }
      }
      if (type != BSDataType.GET) {
        updateBtn.setEnabled(false);
        setSelectedRow(-1);
      }

      String[][] dataTable = findByCriteria();
      table.setModel(new DefaultTableModel(dataTable, BSJRow.getHeaders()));
      setColums(table);
      if (rowSelected > -1) {
        table.getSelectionModel().setSelectionInterval(rowSelected, rowSelected);
        table.repaint();
      }
    }
  }

}
