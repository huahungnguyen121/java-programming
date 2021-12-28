package com.finalExam.views;

import com.finalExam.database.models.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Dialog extends JDialog {
    ArrayList<Student> dataList;

    public Dialog(JFrame parent) {
        super(parent, "View All Students");

        dataList = new Student().getAll();

        this.setSize(500, 500);

        this.setLocationRelativeTo(null);

        Container c = this.getContentPane();

        SpringLayout layout = new SpringLayout();

        c.setLayout(layout);

        String[] columnNames = { "No", "Student ID", "First Name", "Last Name", "Date of birth", "Address" };

        JTable table = new JTable(new DefaultTableModel(columnNames, 0));

        JScrollPane listScroll = new JScrollPane(table);

        c.add(listScroll);

        table.setRowHeight(20);

        table.setEnabled(false);

        layout.putConstraint(SpringLayout.NORTH, listScroll, 10, SpringLayout.NORTH, c);
        layout.putConstraint(SpringLayout.SOUTH, listScroll, -10, SpringLayout.SOUTH, c);
        layout.putConstraint(SpringLayout.WEST, listScroll, 10, SpringLayout.WEST, c);
        layout.putConstraint(SpringLayout.EAST, listScroll, -10, SpringLayout.EAST, c);

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
    }

    public void makeVisible(boolean isVisible) {
        this.setVisible(isVisible);
    }
}
