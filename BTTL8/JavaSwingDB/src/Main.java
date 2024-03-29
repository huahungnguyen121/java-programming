import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

class StudentManagementApp {
    Database db;
    StudentManagementApp() {
        db = new Database();
        JFrame jf = new JFrame("Student Management");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(new GridLayout(0 , 1 , 5, 5));
        jf.setSize(250,350);
        jf.setResizable(true);

        jf.setLocationRelativeTo(null);

        JLabel label = new JLabel("Feature list", JLabel.CENTER);
        jf.add(label);

        JButton viewAllBtn = new JButton("View all students");
        JButton addBtn = new JButton("Add new student");
        JButton searchBtn = new JButton("Search student");
        JButton updateBtn = new JButton("Update student");
        JButton deleteBtn = new JButton("Delete student");

        jf.add(viewAllBtn);
        jf.add(addBtn);
        jf.add(searchBtn);
        jf.add(updateBtn);
        jf.add(deleteBtn);

        viewAllBtn.addActionListener(e -> {
            ArrayList<Student> dataList = db.queryAll();

            JDialog viewAllWindow = new JDialog(jf, "View All Students");

            viewAllWindow.setSize(500, 500);

            viewAllWindow.setLocationRelativeTo(null);

            String[] columnNames = { "No", "Student ID", "First Name", "Last Name", "Date of birth", "Address" };

            JTable table = new JTable(new DefaultTableModel(columnNames, 0));

            JScrollPane listScroll = new JScrollPane(table);

            viewAllWindow.add(listScroll);

            table.setRowHeight(20);

            table.setEnabled(false);

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            for(int i = 0; i < dataList.size(); i++) {
                String[] dataRow = {
                        Integer.toString(i + 1),
                        dataList.get(i).id,
                        dataList.get(i).fName,
                        dataList.get(i).lName,
                        dataList.get(i).DoB.toString(),
                        dataList.get(i).Address
                };

                model.addRow(dataRow);
            }

            viewAllWindow.setVisible(true);
        });

        addBtn.addActionListener(e -> {
            JDialog addWindow = new JDialog(jf, "Add new student");

            Container c = addWindow.getContentPane();

            SpringLayout layout = new SpringLayout();

            c.setLayout(layout);

            addWindow.setLocationRelativeTo(null);

            JPanel formPanel = new JPanel();
            formPanel.setLayout(new SpringLayout());
            c.add(formPanel);

            addWindow.setSize(350, 300);

            JLabel id = new JLabel("Student ID:", JLabel.RIGHT);
            formPanel.add(id);

            JTextField idValue = new JTextField(15);
            formPanel.add(idValue);

            JLabel fName = new JLabel("First name:", JLabel.RIGHT);
            formPanel.add(fName);

            JTextField fNameValue = new JTextField(15);
            formPanel.add(fNameValue);

            JLabel lName = new JLabel("Last name:", JLabel.RIGHT);
            formPanel.add(lName);

            JTextField lNameValue = new JTextField(15);
            formPanel.add(lNameValue);

            JLabel DoB = new JLabel("Date of birth:", JLabel.RIGHT);
            formPanel.add(DoB);

            JTextField DoBValue = new JTextField(15);
            formPanel.add(DoBValue);

            JLabel address = new JLabel("Address:", JLabel.RIGHT);
            formPanel.add(address);

            JTextField addressValue = new JTextField(15);
            formPanel.add(addressValue);

            JPanel buttonPanel = new JPanel(new FlowLayout());
            c.add(buttonPanel);

            JButton confirmButton = new JButton("Confirm");
            buttonPanel.add(confirmButton);

            JLabel status = new JLabel("");
            buttonPanel.add(status);

            JLabel dateFormat = new JLabel("Date of birth must be enter in format YYYY/MM/DD");
            c.add(dateFormat);

            SpringUtilities.makeCompactGrid(formPanel, 5, 2, 5, 5, 5, 5);

            layout.putConstraint(SpringLayout.NORTH, formPanel, 10, SpringLayout.NORTH, c);
            layout.putConstraint(SpringLayout.WEST, formPanel, 10, SpringLayout.WEST, c);
            layout.putConstraint(SpringLayout.EAST, formPanel, -10, SpringLayout.EAST, c);

            layout.putConstraint(SpringLayout.SOUTH, formPanel, -10, SpringLayout.NORTH, buttonPanel);

            layout.putConstraint(SpringLayout.WEST, buttonPanel, 10, SpringLayout.WEST, c);
            layout.putConstraint(SpringLayout.EAST, buttonPanel, -10, SpringLayout.EAST, c);

            layout.putConstraint(SpringLayout.SOUTH, buttonPanel, -10, SpringLayout.NORTH, dateFormat);

            layout.putConstraint(SpringLayout.WEST, dateFormat, 10, SpringLayout.WEST, c);
            layout.putConstraint(SpringLayout.EAST, dateFormat, -10, SpringLayout.EAST, c);
            layout.putConstraint(SpringLayout.SOUTH, dateFormat, -10, SpringLayout.SOUTH, c);

            confirmButton.addActionListener(e12 -> {
                String sql = "INSERT INTO Student(StudentID, FirstName, LastName, DoB, Address) " +
                        "VALUES ('%s', '%s', '%s', '%s', '%s')".formatted(idValue.getText(), fNameValue.getText(), lNameValue.getText(), DoBValue.getText(), addressValue.getText());
                //db = new Database();
                db.queryDB(sql);
                status.setText("Done");
            });

            addWindow.setVisible(true);
        });

        searchBtn.addActionListener(e -> {
            JDialog searchWindow = new JDialog(jf, "Search student");

            searchWindow.setLayout(new FlowLayout());

            searchWindow.setSize(400, 150);

            searchWindow.setLocationRelativeTo(null);

            JLabel label1 = new JLabel("Input search value:", JLabel.RIGHT);
            searchWindow.add(label1);

            JTextField value = new JTextField(15);
            searchWindow.add(value);

            JPanel cmdPanel = new JPanel();
            searchWindow.add(cmdPanel);

            JRadioButton byNameBtn = new JRadioButton("By Name");
            cmdPanel.add(byNameBtn);

            JRadioButton byIDBtn = new JRadioButton("By ID");
            cmdPanel.add(byIDBtn);

            JButton confirmBtn = new JButton("Search");
            cmdPanel.add(confirmBtn);

            ButtonGroup group = new ButtonGroup();
            group.add(byNameBtn);
            group.add(byIDBtn);

            confirmBtn.addActionListener(e13 -> {
                String byName = byNameBtn.isSelected() ? value.getText() : "";
                String byID = byIDBtn.isSelected() ? value.getText() : "";
                ArrayList<Student> dataList = db.searchDB(byID, byName);

                JDialog viewAllWindow = new JDialog(jf, "Search Result");

                viewAllWindow.setSize(500, 500);

                JPanel list = new JPanel(new GridLayout(0, 6));
                JScrollPane listScroll = new JScrollPane(list);
                viewAllWindow.add(listScroll);

                JLabel numLabel = new JLabel("No", JLabel.CENTER);
                list.add(numLabel);
                JLabel idLabel = new JLabel("Student ID", JLabel.CENTER);
                list.add(idLabel);
                JLabel fNameLabel = new JLabel("First name", JLabel.CENTER);
                list.add(fNameLabel);
                JLabel lNameLabel = new JLabel("Last name", JLabel.CENTER);
                list.add(lNameLabel);
                JLabel DoBLabel = new JLabel("Date of birth", JLabel.CENTER);
                list.add(DoBLabel);
                JLabel AddressLabel = new JLabel("Address", JLabel.CENTER);
                list.add(AddressLabel);

                if (dataList != null) {
                    for(int i = 0; i < dataList.size(); i++) {
                        numLabel = new JLabel(Integer.toString(i + 1), JLabel.CENTER);
                        list.add(numLabel);
                        idLabel = new JLabel(dataList.get(i).id, JLabel.CENTER);
                        list.add(idLabel);
                        fNameLabel = new JLabel(dataList.get(i).fName, JLabel.CENTER);
                        list.add(fNameLabel);
                        lNameLabel = new JLabel(dataList.get(i).lName, JLabel.CENTER);
                        list.add(lNameLabel);
                        DoBLabel = new JLabel(dataList.get(i).DoB.toString(), JLabel.CENTER);
                        list.add(DoBLabel);
                        AddressLabel = new JLabel(dataList.get(i).Address, JLabel.CENTER);
                        list.add(AddressLabel);
                    }
                }

                viewAllWindow.setVisible(true);
            });

            searchWindow.setVisible(true);
        });

        updateBtn.addActionListener(e -> {
            JDialog updateWindow = new JDialog(jf, "Update student");

            updateWindow.setLayout(new FlowLayout());

            updateWindow.setSize(400, 150);

            updateWindow.setLocationRelativeTo(null);

            JLabel label12 = new JLabel("Input search value:", JLabel.RIGHT);
            updateWindow.add(label12);

            JTextField value = new JTextField(15);
            updateWindow.add(value);

            JPanel cmdPanel = new JPanel();
            updateWindow.add(cmdPanel);

            JRadioButton byNameBtn = new JRadioButton("By Name");
            cmdPanel.add(byNameBtn);

            JRadioButton byIDBtn = new JRadioButton("By ID");
            cmdPanel.add(byIDBtn);

            JButton confirmBtn = new JButton("Search");
            cmdPanel.add(confirmBtn);

            ButtonGroup group = new ButtonGroup();
            group.add(byNameBtn);
            group.add(byIDBtn);

            confirmBtn.addActionListener(e14 -> {
                String byName = byNameBtn.isSelected() ? value.getText() : "";
                String byID = byIDBtn.isSelected() ? value.getText() : "";
                ArrayList<Student> dataList = db.searchDB(byID, byName);

                JDialog viewAllWindow = new JDialog(jf, "Search Result");

                viewAllWindow.setLayout(new BorderLayout());

                viewAllWindow.setSize(500, 500);

                viewAllWindow.setLocationRelativeTo(null);

                JPanel list = new JPanel(new GridLayout(0, 7));
                JScrollPane listScroll = new JScrollPane(list);
                viewAllWindow.add(listScroll, BorderLayout.CENTER);

                JLabel selectLabel = new JLabel("Select", JLabel.CENTER);
                list.add(selectLabel);
                JLabel numLabel = new JLabel("No", JLabel.CENTER);
                list.add(numLabel);
                JLabel idLabel = new JLabel("Student ID", JLabel.CENTER);
                list.add(idLabel);
                JLabel fNameLabel = new JLabel("First name", JLabel.CENTER);
                list.add(fNameLabel);
                JLabel lNameLabel = new JLabel("Last name", JLabel.CENTER);
                list.add(lNameLabel);
                JLabel DoBLabel = new JLabel("Date of birth", JLabel.CENTER);
                list.add(DoBLabel);
                JLabel AddressLabel = new JLabel("Address", JLabel.CENTER);
                list.add(AddressLabel);

                ButtonGroup selectGroup = new ButtonGroup();

                ArrayList<JRadioButton> buttonList = new ArrayList<>();

                if (dataList != null) {
                    for(int i = 0; i < dataList.size(); i++) {
                        JRadioButton selectBtn = new JRadioButton();
                        list.add(selectBtn);
                        buttonList.add(selectBtn);
                        selectGroup.add(selectBtn);
                        numLabel = new JLabel(Integer.toString(i + 1), JLabel.CENTER);
                        list.add(numLabel);
                        idLabel = new JLabel(dataList.get(i).id, JLabel.CENTER);
                        list.add(idLabel);
                        fNameLabel = new JLabel(dataList.get(i).fName, JLabel.CENTER);
                        list.add(fNameLabel);
                        lNameLabel = new JLabel(dataList.get(i).lName, JLabel.CENTER);
                        list.add(lNameLabel);
                        DoBLabel = new JLabel(dataList.get(i).DoB.toString(), JLabel.CENTER);
                        list.add(DoBLabel);
                        AddressLabel = new JLabel(dataList.get(i).Address, JLabel.CENTER);
                        list.add(AddressLabel);
                    }
                }

                JButton selectRecordBtn = new JButton("Process to edit");
                viewAllWindow.add(selectRecordBtn, BorderLayout.SOUTH);

                selectRecordBtn.addActionListener(e141 -> {
                    for(int i = 0; i < buttonList.size(); i++) {
                        if (buttonList.get(i).isSelected()) {
                            JDialog updateWindow1 = new JDialog(jf, "Update student");
                            updateWindow1.setLayout(new FlowLayout());

                            updateWindow1.setLocationRelativeTo(null);

                            JPanel formPanel = new JPanel(new GridLayout(0, 2));
                            updateWindow1.add(formPanel);

                            updateWindow1.setSize(350, 300);

                            JLabel id = new JLabel("Student ID:", JLabel.RIGHT);
                            formPanel.add(id);

                            JTextField idValue = new JTextField(15);
                            formPanel.add(idValue);

                            JLabel fName = new JLabel("First name:", JLabel.RIGHT);
                            formPanel.add(fName);

                            JTextField fNameValue = new JTextField(15);
                            formPanel.add(fNameValue);

                            JLabel lName = new JLabel("Last name:", JLabel.RIGHT);
                            formPanel.add(lName);

                            JTextField lNameValue = new JTextField(15);
                            formPanel.add(lNameValue);

                            JLabel DoB = new JLabel("Date of birth:", JLabel.RIGHT);
                            formPanel.add(DoB);

                            JTextField DoBValue = new JTextField(15);
                            formPanel.add(DoBValue);

                            JLabel address = new JLabel("Address:", JLabel.RIGHT);
                            formPanel.add(address);

                            JTextField addressValue = new JTextField(15);
                            formPanel.add(addressValue);

                            if (dataList != null) {
                                idValue.setText(dataList.get(i).id);

                                fNameValue.setText(dataList.get(i).fName);

                                lNameValue.setText(dataList.get(i).lName);

                                DoBValue.setText(dataList.get(i).DoB.toString());

                                addressValue.setText(dataList.get(i).Address);
                            }

                            JPanel buttonPanel = new JPanel(new FlowLayout());
                            updateWindow1.add(buttonPanel);

                            JButton confirmButton = new JButton("Confirm");
                            buttonPanel.add(confirmButton);

                            JLabel status = new JLabel("", JLabel.CENTER);
                            buttonPanel.add(status);

                            JLabel dateFormat = new JLabel("Date of birth must be enter in format YYYY/MM/DD");
                            updateWindow1.add(dateFormat);

                            confirmButton.addActionListener(e1411 -> {
                                String sql = "UPDATE Student " +
                                        "SET FirstName = '%s', LastName = '%s', DoB = '%s', Address = '%s' WHERE StudentID = '%s'".formatted(fNameValue.getText(), lNameValue.getText(), DoBValue.getText(), addressValue.getText(), idValue.getText());
                                db.queryDB(sql);
                                status.setText("Done");
                            });

                            updateWindow1.setVisible(true);
                        }
                    }
                });

                viewAllWindow.setVisible(true);
            });

            updateWindow.setVisible(true);
        });

        deleteBtn.addActionListener(e -> {
            JDialog deleteWindow = new JDialog(jf, "Delete student");

            deleteWindow.setLayout(new FlowLayout());

            deleteWindow.setSize(400, 150);

            deleteWindow.setLocationRelativeTo(null);

            JLabel label13 = new JLabel("Input delete value:", JLabel.RIGHT);
            deleteWindow.add(label13);

            JTextField value = new JTextField(15);
            deleteWindow.add(value);

            JPanel cmdPanel = new JPanel();
            deleteWindow.add(cmdPanel);

            JRadioButton byNameBtn = new JRadioButton("By Name");
            cmdPanel.add(byNameBtn);

            JRadioButton byIDBtn = new JRadioButton("By ID");
            cmdPanel.add(byIDBtn);

            JButton confirmBtn = new JButton("Delete");
            cmdPanel.add(confirmBtn);

            ButtonGroup group = new ButtonGroup();
            group.add(byNameBtn);
            group.add(byIDBtn);

            confirmBtn.addActionListener(e15 -> {
                String byName = byNameBtn.isSelected() ? value.getText() : "";
                String byID = byIDBtn.isSelected() ? value.getText() : "";
                ArrayList<Student> dataList = db.searchDB(byID, byName);

                JDialog viewAllWindow = new JDialog(jf, "Search Result");

                viewAllWindow.setLayout(new BorderLayout());

                viewAllWindow.setSize(500, 500);

                JLabel status = new JLabel("");
                viewAllWindow.add(status, BorderLayout.NORTH);

                JPanel list = new JPanel(new GridLayout(0, 7));
                JScrollPane listScroll = new JScrollPane(list);
                viewAllWindow.add(listScroll, BorderLayout.CENTER);

                JLabel selectLabel = new JLabel("Select", JLabel.CENTER);
                list.add(selectLabel);
                JLabel numLabel = new JLabel("No", JLabel.CENTER);
                list.add(numLabel);
                JLabel idLabel = new JLabel("Student ID", JLabel.CENTER);
                list.add(idLabel);
                JLabel fNameLabel = new JLabel("First name", JLabel.CENTER);
                list.add(fNameLabel);
                JLabel lNameLabel = new JLabel("Last name", JLabel.CENTER);
                list.add(lNameLabel);
                JLabel DoBLabel = new JLabel("Date of birth", JLabel.CENTER);
                list.add(DoBLabel);
                JLabel AddressLabel = new JLabel("Address", JLabel.CENTER);
                list.add(AddressLabel);

                ButtonGroup selectGroup = new ButtonGroup();

                ArrayList<JRadioButton> buttonList = new ArrayList<>();

                if (dataList != null) {
                    for(int i = 0; i < dataList.size(); i++) {
                        JRadioButton selectBtn = new JRadioButton();
                        list.add(selectBtn);
                        buttonList.add(selectBtn);
                        selectGroup.add(selectBtn);
                        numLabel = new JLabel(Integer.toString(i + 1), JLabel.CENTER);
                        list.add(numLabel);
                        idLabel = new JLabel(dataList.get(i).id, JLabel.CENTER);
                        list.add(idLabel);
                        fNameLabel = new JLabel(dataList.get(i).fName, JLabel.CENTER);
                        list.add(fNameLabel);
                        lNameLabel = new JLabel(dataList.get(i).lName, JLabel.CENTER);
                        list.add(lNameLabel);
                        DoBLabel = new JLabel(dataList.get(i).DoB.toString(), JLabel.CENTER);
                        list.add(DoBLabel);
                        AddressLabel = new JLabel(dataList.get(i).Address, JLabel.CENTER);
                        list.add(AddressLabel);
                    }
                }

                JButton selectRecordBtn = new JButton("Delete selected item");
                viewAllWindow.add(selectRecordBtn, BorderLayout.SOUTH);

                selectRecordBtn.addActionListener(e1 -> {
                    for(int i = 0; i < buttonList.size(); i++) {
                        if (buttonList.get(i).isSelected()) {
                            String sql;
                            if (dataList != null) {
                                sql = "DELETE FROM Student " +
                                        "WHERE StudentID = '%s'".formatted(dataList.get(i).id);

                                db.queryDB(sql);
                                status.setText("Done");
                            }
                        }
                    }
                });

                viewAllWindow.setVisible(true);
            });

            deleteWindow.setVisible(true);
        });
        jf.setVisible(true);
    }
}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementApp::new);
    }
}