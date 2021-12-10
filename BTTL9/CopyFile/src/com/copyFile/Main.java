package com.copyFile;

import javax.swing.*;
import java.awt.*;

class progressWindow extends JFrame {

    static JProgressBar progressBar;

    static short maxValue;

    progressWindow() {
        maxValue = 100;
        this.setTitle("Copy Progress");
        this.setSize(300, 100);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JLabel title = new JLabel("Progress");
        this.add(title);
        progressBar = new JProgressBar(0, 100);
        this.add(progressBar);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        progressBar.setValue(0);
    }

    public void setProgressBar(int value) {
        progressBar.setValue(value);
    }
}

public class Main extends Thread {
    public void run() {
        progressWindow mainWindow = new progressWindow();
        mainWindow.setVisible(true);

        int n = 10000;

        for (int i = 0; i < n; i++) {
            try {
                mainWindow.setProgressBar(i * 100 / n);
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        }
        mainWindow.setProgressBar(100);
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.start();
        try {
            m.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }
}
