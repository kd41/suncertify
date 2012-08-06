package suncertify.gui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
    JFrame frame = new JFrame("Bodgitt and Scarper, LLC");
    frame.setLayout(new BorderLayout());
    String data[][] = findByCriteria(new String[] { null, null, null, null, null, null });
    String[] columnNames = BSJRow.getHeaders();

    // table
    final JTable table = new JTable(data, columnNames);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.getColumnModel().getColumn(3).setPreferredWidth(200);
    JTableHeader header = table.getTableHeader();
    header.setBackground(Color.yellow);
    JScrollPane pane = new JScrollPane(table);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    setColumsWidth(table);

    // left panel
    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
    // refresh button
    JButton btnRefresh = new JButton("Refresh");
    btnRefresh.addActionListener(new BSActionListener(table, BSDataType.REFRESH));
    leftPanel.add(btnRefresh);
    JPanel searchPanel = new JPanel();
    searchPanel.setMaximumSize(new Dimension(220, 200));
    searchPanel.setBorder(BorderFactory.createTitledBorder("Search and Create"));
    GridBagLayout gbl = new GridBagLayout();
    searchPanel.setLayout(gbl);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.NONE;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.ipadx = 10;
    JLabel label = new JLabel("Name");
    gbl.setConstraints(label, gbc);
    searchPanel.add(label);
    gbc.ipadx = 120;
    gbc.gridx = 1;
    final JTextField tfName = new JTextField(10);
    tfName.setText("test");
    gbl.setConstraints(tfName, gbc);
    searchPanel.add(tfName);
    label = new JLabel("Location");
    gbc.ipadx = 10;
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbl.setConstraints(label, gbc);
    searchPanel.add(label);
    gbc.ipadx = 120;
    gbc.gridx = 1;
    final JTextField tfLocation = new JTextField(10);
    gbl.setConstraints(tfLocation, gbc);
    searchPanel.add(tfLocation);
    label = new JLabel("Specialties");
    gbc.ipadx = 10;
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbl.setConstraints(label, gbc);
    searchPanel.add(label);
    gbc.ipadx = 120;
    gbc.gridx = 1;
    final JTextField tfSpecialties = new JTextField(10);
    gbl.setConstraints(tfSpecialties, gbc);
    searchPanel.add(tfSpecialties);
    label = new JLabel("Nr workers");
    gbc.ipadx = 10;
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbl.setConstraints(label, gbc);
    searchPanel.add(label);
    gbc.ipadx = 120;
    gbc.gridx = 1;
    final JTextField tfWorkers = new JTextField(10);
    gbl.setConstraints(tfWorkers, gbc);
    searchPanel.add(tfWorkers);
    label = new JLabel("Rate");
    gbc.ipadx = 10;
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbl.setConstraints(label, gbc);
    searchPanel.add(label);
    gbc.ipadx = 120;
    gbc.gridx = 1;
    final JTextField tfRate = new JTextField(10);
    gbl.setConstraints(tfRate, gbc);
    searchPanel.add(tfRate);
    label = new JLabel("Owner");
    gbc.ipadx = 10;
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbl.setConstraints(label, gbc);
    searchPanel.add(label);
    gbc.ipadx = 120;
    gbc.gridx = 1;
    final JTextField tfOwner = new JTextField(10);
    gbl.setConstraints(tfOwner, gbc);
    searchPanel.add(tfOwner);
    gbc.ipadx = 0;
    gbc.gridx = 0;
    gbc.gridwidth = 2;
    gbc.gridy = 6;
    JButton btnSearch = new JButton("Search");
    gbl.setConstraints(btnSearch, gbc);
    btnSearch.addActionListener(new BSActionListener(table, BSDataType.SEARCH, new JTextField[] { tfName, tfLocation,
                                                                                                 tfSpecialties,
                                                                                                 tfWorkers, tfRate,
                                                                                                 tfOwner }) {
    });
    searchPanel.add(btnSearch);
    gbc.gridy = 7;
    JButton btnCreate = new JButton("Create");
    gbl.setConstraints(btnCreate, gbc);
    searchPanel.add(btnCreate);
    btnCreate.addActionListener(new BSActionListener(table, BSDataType.CREATE, new JTextField[] { tfName, tfLocation,
                                                                                                 tfSpecialties,
                                                                                                 tfWorkers, tfRate,
                                                                                                 tfOwner }) {
    });

    leftPanel.add(searchPanel);

    // Delete row
    JButton btnDelete = new JButton("Delete selected row");
    btnDelete.addActionListener(new BSActionListener(table, BSDataType.DELETE));
    leftPanel.add(btnDelete);

    leftPanel.add(new JButton("Some Btn2"));

    frame.add(leftPanel, BorderLayout.LINE_START);
    frame.add(pane, BorderLayout.CENTER);
    frame.setSize(1480, 650);
    frame.setUndecorated(true);
    frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  private void setColumsWidth(JTable table) {
    for (int i = 0; i < BSJRow.getHeaders().length; i++) {
      TableColumn col = table.getColumnModel().getColumn(i);
      switch (i) {
      case 0:
        col.setPreferredWidth(50);
        break;
      case 1:
        col.setPreferredWidth(50);
        break;
      case 2:
        col.setPreferredWidth(50);
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
        col.setPreferredWidth(100);
        break;
      case 7:
        col.setPreferredWidth(100);
        break;
      case 8:
        col.setPreferredWidth(50);
        break;
      }
    }
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
      if (type == BSDataType.REFRESH) {

      } else if (type == BSDataType.DELETE) {
        int rowSelected = table.getSelectedRow();
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
      }

      String[][] dataTable = findByCriteria(criteria);
      table.setModel(new DefaultTableModel(dataTable, BSJRow.getHeaders()));
      setColumsWidth(table);
    }
  }

}
