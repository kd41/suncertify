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
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
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
import java.io.IOException;

import suncertify.parser.DBPresenter;
import suncertify.program.Mode;

public class BSJFrameBase extends BSData {
  protected JTable table;

  protected JButton refreshBtn;
  protected JButton searchBtn;
  protected JButton createBtn;
  protected JButton deleteBtn;
  protected JButton getBtn;
  protected JButton updateBtn;

  protected JTextField dbLocationField;
  protected JTextField dbHostField;
  protected JTextField dbPortField;
  protected JTextField nameField;
  protected JTextField locationField;
  protected JTextField specialitiesField;
  protected JTextField workersNrField;
  protected JTextField rateField;
  protected JTextField ownerField;

  protected JLabel statusLabel;

  public static void main(String... args) throws IOException {
    // TODO: main method is only for test
    new BSJFrameBase(Mode.NETWORK_CLIENT_AND_GUI);
  }

  public BSJFrameBase(Mode mode) {
    JFrame jFrame = new JFrame("Bodgitt and Scarper, LLC");
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.setSize(1100, 800);
    jFrame.setLayout(new BorderLayout());

    String data[][] = findByCriteria();
    String[] columnNames = BSJRow.getHeaders();
    final BSCellRenderer renderer = new BSCellRenderer();
    table = new JTable(data, columnNames) {
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

    JPanel dbPanel = new JPanel();
    dbPanel.setBorder(BorderFactory.createTitledBorder("Database location:"));
    GridBagLayout dbgbl = new GridBagLayout();
    GridBagConstraints dbgbc = new GridBagConstraints();
    dbgbc.insets = new Insets(0, 0, 2, 0);
    dbgbc.anchor = GridBagConstraints.LINE_END;
    JLabel hostLbl = new JLabel("Host:");
    dbgbl.setConstraints(hostLbl, dbgbc);
    dbPanel.add(hostLbl);
    dbgbc.gridx = 1;
    Component dbgap = Box.createRigidArea(new Dimension(5, 5));
    dbgbl.setConstraints(dbgap, dbgbc);
    dbPanel.add(dbgap);
    dbgbc.gridx = 2;
    dbgbc.anchor = GridBagConstraints.LINE_START;
    dbHostField = new JTextField(10);
    dbHostField.setText(DBPresenter.getInstance().getDbHost());
    dbgbl.setConstraints(dbHostField, dbgbc);
    dbPanel.add(dbHostField);
    dbgbc.gridx = 0;
    dbgbc.gridy = 1;
    dbgbc.anchor = GridBagConstraints.LINE_END;
    JLabel portLbl = new JLabel("Port:");
    dbgbl.setConstraints(portLbl, dbgbc);
    dbPanel.add(portLbl);
    dbgbc.gridx = 2;
    dbgbc.anchor = GridBagConstraints.LINE_START;
    dbPortField = new JTextField(10);
    dbPortField.setText(DBPresenter.getInstance().getDbPort());
    dbgbl.setConstraints(dbPortField, dbgbc);
    dbPanel.add(dbPortField);
    dbgbc.gridx = 0;
    dbgbc.gridy = 2;
    dbgbc.anchor = GridBagConstraints.LINE_END;
    JLabel pathLbl = new JLabel("Path:");
    dbgbl.setConstraints(pathLbl, dbgbc);
    dbPanel.add(pathLbl);
    dbgbc.gridx = 2;
    dbgbc.anchor = GridBagConstraints.LINE_START;
    dbLocationField = new JTextField(10);
    dbLocationField.setText(DBPresenter.getInstance().getDbPath());
    dbgbl.setConstraints(dbLocationField, dbgbc);
    dbPanel.add(dbLocationField);
    dbPanel.setLayout(dbgbl);
    gbl.setConstraints(dbPanel, gbc);
    buttonPanel.add(dbPanel);

    gbc.gridy = 2;
    gbc.anchor = GridBagConstraints.LINE_END;
    refreshBtn = new JButton("Refresh");
    gbl.setConstraints(refreshBtn, gbc);
    buttonPanel.add(refreshBtn);

    gbc.gridy = 3;
    gbc.insets = new Insets(0, 0, 5, 0);
    JPanel searchPanel = new JPanel();
    searchPanel.setBorder(BorderFactory.createTitledBorder("Search and Create"));
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
    nameField = new JTextField(10);
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
    locationField = new JTextField(10);
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
    specialitiesField = new JTextField(10);
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
    workersNrField = new JTextField(10);
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
    rateField = new JTextField(10);
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
    ownerField = new JTextField(10);
    sgbl.setConstraints(ownerField, sgbc);
    searchPanel.add(ownerField);
    sgbc.gridy = 6;
    sgbc.gridwidth = 3;
    sgbc.anchor = GridBagConstraints.LINE_END;
    searchBtn = new JButton("Search");
    sgbl.setConstraints(searchBtn, sgbc);
    searchPanel.add(searchBtn);
    sgbc.gridy = 7;
    createBtn = new JButton("Create");
    sgbl.setConstraints(createBtn, sgbc);
    searchPanel.add(createBtn);
    sgbc.gridy = 8;
    getBtn = new JButton("Get");
    sgbl.setConstraints(getBtn, sgbc);
    searchPanel.add(getBtn);
    sgbc.gridy = 9;
    updateBtn = new JButton("Update");
    sgbl.setConstraints(updateBtn, sgbc);
    searchPanel.add(updateBtn);

    searchPanel.setLayout(sgbl);
    gbl.setConstraints(searchPanel, gbc);
    buttonPanel.add(searchPanel);

    gbc.gridy = 4;
    deleteBtn = new JButton("Delete selected row");
    gbl.setConstraints(deleteBtn, gbc);
    buttonPanel.add(deleteBtn);

    gbc.gridy = 5;
    statusLabel = new JLabel("Started");
    statusLabel.setForeground(Color.BLUE);
    gbl.setConstraints(statusLabel, gbc);
    buttonPanel.add(statusLabel);

    buttonPanel.setLayout(gbl);

    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    JTableHeader header = table.getTableHeader();
    header.setBackground(Color.yellow);
    JScrollPane dataPane = new JScrollPane(table);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    jFrame.add(buttonPanel, BorderLayout.LINE_START);
    jFrame.add(dataPane, BorderLayout.CENTER);

    // TODO: hide elements depends on run mode
    // if (mode == Mode.NETWORK_CLIENT_AND_GUI) {
    // dbLocationField.setVisible(false);
    // } else if (mode == Mode.STANDALONE) {
    // dbHostField.setVisible(false);
    // dbPortField.setVisible(false);
    // }

    jFrame.pack();
    jFrame.setVisible(true);
  }

  protected void setStatus(String text) {
    setStatus(text, true);
  }

  protected void setStatus(String text, boolean isSuccess) {
    statusLabel.setText(text);
    if (isSuccess) {
      statusLabel.setForeground(Color.BLUE);
    } else {
      statusLabel.setForeground(Color.RED);
    }
  }

  protected void setColums(JTable table) {
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
        col.setPreferredWidth(30);
        break;
      default:
        break;
      }
    }
    // TODO: need or not to need?
    // table.getColumnModel().getColumn(BSJRow.VALID).setMinWidth(0);
    // table.getColumnModel().getColumn(BSJRow.VALID).setMaxWidth(0);
    // table.getColumnModel().getColumn(BSJRow.VALID).setWidth(0);
    // table.getColumnModel().getColumn(BSJRow.OWNER).setMinWidth(0);
    // table.getColumnModel().getColumn(BSJRow.OWNER).setMaxWidth(0);
    // table.getColumnModel().getColumn(BSJRow.OWNER).setWidth(0);

  }

  private class BSCellRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
      Component rendererComp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if (!isSelected) {
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
