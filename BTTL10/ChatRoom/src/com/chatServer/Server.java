package com.chatServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame implements Runnable{
    JTextArea textArea = null;
    JScrollPane scrollPane = null;
    SpringLayout layout = null;
    JLabel label = null;

    static ServerSocket server = null;
    static int port = 3500;
    static int MAX_CONNECTION;
    volatile boolean closeSV = false;

    public Server() {
        while (true) {
            String max = JOptionPane.showInputDialog(this,
                    "Please input the maximum number of allowed connections: ", "Request information",
                    JOptionPane.QUESTION_MESSAGE);

            if (max == null) {
                System.exit(0);
            } else if (max.equals("")) {
                JOptionPane.showMessageDialog(this,
                        "Your input is not valid.", "Invalid input",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    MAX_CONNECTION = Integer.parseInt(max);
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this,
                            "Your input is not valid.", "Invalid input",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        createWindow();

        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Error while creating socket.");
            System.exit(1);
        }

        textArea.append("Server socket was created successfully.\n");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    server.close();
                    closeSV = true;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (ServerThread.closeAll())
                    System.exit(0);
                else {
                    System.exit(1);
                }
            }
        });
    }

    private void createWindow() {
        this.setTitle("Server");

        this.setSize(550, 400);

        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container container = this.getContentPane();

        layout = new SpringLayout();

        container.setLayout(layout);

        textArea = new JTextArea(10,25);
        textArea.setEditable(false);

        scrollPane = new JScrollPane(textArea);

        scrollPane.setPreferredSize(new Dimension(300, 250));

        container.add(scrollPane);

        label = new JLabel("Max Connection: " + MAX_CONNECTION);

        container.add(label);

        layout.putConstraint(SpringLayout.WEST, scrollPane, 5, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.EAST, scrollPane, -5, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 5, SpringLayout.NORTH, container);

        layout.putConstraint(SpringLayout.SOUTH, scrollPane, -5, SpringLayout.NORTH, label);

        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.SOUTH, label, -5, SpringLayout.SOUTH, container);
    }

    public static void main(String[] args) {
        Server sv = new Server();
        sv.setVisible(true);
        Thread t = new Thread(sv);
        t.start();
    }

    @Override
    public void run() {
        int counter = 0;

        while (!server.isClosed()) {
            try {
                textArea.append("Waiting for connection.....\n");
                Socket clientSocket = server.accept();

                ServerThread client = new ServerThread(clientSocket, textArea, MAX_CONNECTION, "Thread #" + counter);

                client.start();

                counter++;
            } catch (IOException e) {
                if (!closeSV)
                    System.out.println("Connect Error");
            }
        }
    }
}
