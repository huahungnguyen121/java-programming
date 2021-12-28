package com.finalExam.dialogs;

import com.finalExam.database.Database;

import com.finalExam.SpringUtilities;

import javax.swing.*;
import java.awt.*;

public class Dialog2 extends JDialog {
    Database db;

    public Dialog2(JFrame parent, Database database) {
        super(parent, "Add new student");

        db = database;

        Container c = this.getContentPane();

        SpringLayout layout = new SpringLayout();

        c.setLayout(layout);

        this.setLocationRelativeTo(null);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new SpringLayout());
        c.add(formPanel);

        this.setSize(350, 300);

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
            db.queryDB(sql);
            status.setText("Done");
        });
    }

    public void makeVisible(boolean isVisible) {
        this.setVisible(isVisible);
    }
}
