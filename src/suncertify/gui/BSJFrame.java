package suncertify.gui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BSJFrame extends BSData {

  public static void main(String... args) throws IOException {
    // TODO: main method is only for test
    new BSJFrame();
  }

  public BSJFrame() {
    JFrame jFrame = new JFrame("Bodgitt and Scarper, LLC");
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.setSize(1100, 600);
    jFrame.setLayout(new BorderLayout());

    String data[][] = findByCriteria(new String[] { null, null, null, null, null, null });
    String[] columnNames = BSJRow.getHeaders();
    final BSCellRenderer renderer = new BSCellRenderer();
    final JTable table = new JTable(data, columnNames) {
      private static final long serialVersionUID = 1L;

      @Override
      public TableCellRenderer getCellRenderer(int row, int column) {
        return renderer;
      }
    };

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                                                             BorderFactory.createEmptyBorder(5, 5, 5, 5)));

    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 0, 5, 0);
    gbc.anchor = GridBagConstraints.LINE_END;
    JButton refreshBtn = new JButton("Refresh");
    gbl.setConstraints(refreshBtn, gbc);
    refreshBtn.addActionListener(new BSActionListener(table, BSDataType.REFRESH));
    buttonPanel.add(refreshBtn);

    gbc.gridy = 1;
    gbc.insets = new Insets(0, 0, 5, 0);
    JPanel searchPanel = new JPanel();
    searchPanel.setBorder(BorderFactory.createTitledBorder("Search"));
    GridBagLayout sgbl = new GridBagLayout();
    GridBagConstraints sgbc = new GridBagConstraints();
    sgbc.insets = new Insets(0, 0, 2, 0);
    sgbc.anchor = GridBagConstraints.LINE_END;
    JLabel nameLbl = new JLabel("Name:");
    sgbl.setConstraints(nameLbl, sgbc);
    searchPanel.add(nameLbl);
    sgbc.gridx = 1;
    Component gap = Box.createRigidArea(new Dimension(5, 5));
    sgbl.setConstraints(gap, sgbc);
    searchPanel.add(gap);
    sgbc.gridx = 2;
    sgbc.anchor = GridBagConstraints.LINE_START;
    JTextField nameField = new JTextField(10);
    sgbl.setConstraints(nameField, sgbc);
    searchPanel.add(nameField);
    sgbc.gridx = 0;
    sgbc.gridy = 1;
    sgbc.anchor = GridBagConstraints.LINE_END;
    JLabel locationLbl = new JLabel("Location:");
    sgbl.setConstraints(locationLbl, sgbc);
    searchPanel.add(locationLbl);
    sgbc.gridx = 2;
    sgbc.anchor = GridBagConstraints.LINE_START;
    JTextField locationField = new JTextField(10);
    sgbl.setConstraints(locationField, sgbc);
    searchPanel.add(locationField);
    sgbc.gridx = 0;
    sgbc.gridy = 2;
    sgbc.anchor = GridBagConstraints.LINE_END;
    JLabel specialitiesLbl = new JLabel("Specialities:");
    sgbl.setConstraints(specialitiesLbl, sgbc);
    searchPanel.add(specialitiesLbl);
    sgbc.gridx = 2;
    sgbc.anchor = GridBagConstraints.LINE_START;
    JTextField specialitiesField = new JTextField(10);
    sgbl.setConstraints(specialitiesField, sgbc);
    searchPanel.add(specialitiesField);
    sgbc.gridx = 0;
    sgbc.gridy = 3;
    sgbc.anchor = GridBagConstraints.LINE_END;
    JLabel workersNrLbl = new JLabel("Nr. workers:");
    sgbl.setConstraints(workersNrLbl, sgbc);
    searchPanel.add(workersNrLbl);
    sgbc.gridx = 2;
    sgbc.anchor = GridBagConstraints.LINE_START;
    JTextField workersNrField = new JTextField(10);
    sgbl.setConstraints(workersNrField, sgbc);
    searchPanel.add(workersNrField);
    sgbc.gridx = 0;
    sgbc.gridy = 4;
    sgbc.anchor = GridBagConstraints.LINE_END;
    JLabel rateLbl = new JLabel("Rate:");
    sgbl.setConstraints(rateLbl, sgbc);
    searchPanel.add(rateLbl);
    sgbc.gridx = 2;
    sgbc.anchor = GridBagConstraints.LINE_START;
    JTextField rateField = new JTextField(10);
    sgbl.setConstraints(rateField, sgbc);
    searchPanel.add(rateField);
    sgbc.gridx = 0;
    sgbc.gridy = 5;
    sgbc.anchor = GridBagConstraints.LINE_END;
    JLabel ownerLbl = new JLabel("Owner:");
    sgbl.setConstraints(ownerLbl, sgbc);
    searchPanel.add(ownerLbl);
    sgbc.gridx = 2;
    sgbc.anchor = GridBagConstraints.LINE_START;
    JTextField ownerField = new JTextField(10);
    sgbl.setConstraints(ownerField, sgbc);
    searchPanel.add(ownerField);
    sgbc.gridy = 6;
    sgbc.gridwidth = 3;
    sgbc.anchor = GridBagConstraints.LINE_END;
    JButton searchBtn = new JButton("Search");
    sgbl.setConstraints(searchBtn, sgbc);
    searchBtn.addActionListener(new BSActionListener(table, BSDataType.SEARCH,
                                                     new JTextField[] { nameField, locationField, specialitiesField,
                                                                       workersNrField, rateField, ownerField }) {
    });
    searchPanel.add(searchBtn);
    sgbc.gridy = 7;
    JButton createBtn = new JButton("Create");
    sgbl.setConstraints(createBtn, sgbc);
    createBtn.addActionListener(new BSActionListener(table, BSDataType.CREATE,
                                                     new JTextField[] { nameField, locationField, specialitiesField,
                                                                       workersNrField, rateField, ownerField }) {
    });
    searchPanel.add(createBtn);

    searchPanel.setLayout(sgbl);
    gbl.setConstraints(searchPanel, gbc);
    buttonPanel.add(searchPanel);

    gbc.gridy = 2;
    JButton deleteBtn = new JButton("Delete selected row");
    gbl.setConstraints(deleteBtn, gbc);
    deleteBtn.addActionListener(new BSActionListener(table, BSDataType.DELETE));
    buttonPanel.add(deleteBtn);

    gbc.gridy = 3;
    gbc.anchor = GridBagConstraints.NORTHEAST;
    JButton lockBtn = new JButton("Lock row");
    gbl.setConstraints(lockBtn, gbc);
    lockBtn.addActionListener(new BSActionListener(table, BSDataType.LOCK));
    buttonPanel.add(lockBtn);

    gbc.weighty = 1;
    gbc.gridy = 4;
    JButton unlockBtn = new JButton("Unlock row");
    gbl.setConstraints(unlockBtn, gbc);
    unlockBtn.addActionListener(new BSActionListener(table, BSDataType.UNLOCK));
    buttonPanel.add(unlockBtn);

    buttonPanel.setLayout(gbl);

    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.getColumnModel().getColumn(3).setPreferredWidth(200);
    setColums(table);

    JTableHeader header = table.getTableHeader();
    header.setBackground(Color.yellow);
    JScrollPane dataPane = new JScrollPane(table);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    jFrame.add(buttonPanel, BorderLayout.LINE_START);
    jFrame.add(dataPane, BorderLayout.CENTER);

    jFrame.pack();
    jFrame.setVisible(true);
  }

  private void setColums(JTable table) {
    for (int i = 0; i < BSJRow.getHeaders().length; i++) {
      TableColumn col = table.getColumnModel().getColumn(i);
      switch (i) {
      case 0:
        col.setPreferredWidth(40);
        break;
      case 1:
        col.setPreferredWidth(50);
        break;
      case 2:
        col.setPreferredWidth(30);
        break;
      case 3:
        col.setPreferredWidth(400);
        break;
      case 4:
        col.setPreferredWidth(400);
        break;
      case 5:
        col.setPreferredWidth(400);
        break;
      case 6:
        col.setPreferredWidth(70);
        break;
      case 7:
        col.setPreferredWidth(60);
        break;
      case 8:
        col.setPreferredWidth(50);
        break;
      case 9:
        col.setPreferredWidth(500);
        break;
      default:
        break;
      }
    }
    // set cookie column not visible
    // table.getColumnModel().getColumn(9).setMinWidth(0);
    // table.getColumnModel().getColumn(9).setMaxWidth(0);
    // table.getColumnModel().getColumn(9).setWidth(0);

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
      String[] criteria = new String[] { null, null, null, null, null, null };
      int rowSelected = table.getSelectedRow();
      if (type == BSDataType.REFRESH) {

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
        criteria = new String[] { data[0].getText(), data[1].getText(), data[2].getText(), data[3].getText(),
                                 data[4].getText(), data[5].getText() };
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

      String[][] dataTable = findByCriteria(criteria);
      table.setModel(new DefaultTableModel(dataTable, BSJRow.getHeaders()));
      setColums(table);
    }

  }

  private class BSCellRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
      Component rendererComp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if (!isSelected) {
        long cookie = Long.parseLong((String) table.getModel().getValueAt(row, 9));
        if (cookie > 0) {
          rendererComp.setForeground(Color.red);
        } else {
          rendererComp.setForeground(Color.black);
        }
        if (row % 2 == 0) {
          rendererComp.setBackground(Color.getHSBColor(9, 99, 99));
        } else {
          rendererComp.setBackground(Color.white);
        }
      }
      return rendererComp;
    }
  }

}
