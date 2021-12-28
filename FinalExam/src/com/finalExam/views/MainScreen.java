package com.finalExam.views;

import com.finalExam.database.Database;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    public MainScreen() {
        this.setTitle("Student Management");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(0 , 1 , 5, 5));
        this.setSize(250,350);
        this.setResizable(false);

        this.setLocationRelativeTo(null);

        JLabel label = new JLabel("Feature list", JLabel.CENTER);
        this.add(label);

        JButton viewAllBtn = new JButton("View all students");
        JButton addBtn = new JButton("Add new student");
        JButton searchBtn = new JButton("Search student");
        JButton updateBtn = new JButton("Update student");
        JButton deleteBtn = new JButton("Delete student");

        this.add(viewAllBtn);
        this.add(addBtn);
        this.add(searchBtn);
        this.add(updateBtn);
        this.add(deleteBtn);

        viewAllBtn.addActionListener(e -> {
            Dialog viewAll = new Dialog(this);
            viewAll.makeVisible(true);
        });

        addBtn.addActionListener(e -> {
            Dialog2 add = new Dialog2(this);
            add.makeVisible(true);
        });
    }

    public void makeVisible(boolean isVisible) {
        this.setVisible(isVisible);
    }
}
