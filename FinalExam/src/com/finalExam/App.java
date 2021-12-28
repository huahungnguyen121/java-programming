package com.finalExam;

import com.finalExam.views.MainScreen;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.makeVisible(true);
        });
    }
}
