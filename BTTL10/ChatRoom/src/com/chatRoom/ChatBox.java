package com.chatRoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import com.Constant;

public class ChatBox extends JFrame implements Runnable {
    JTextArea textArea = null;
    JScrollPane scrollPane = null;
    JTextField textField = null;
    SpringLayout layout = null;
    JLabel label = null;
    String name;
    Socket soc = null;
    volatile boolean safeClose = false;

    public ChatBox() {
        while (true) {
            name = JOptionPane.showInputDialog(this,
                    "Please input your name: ", "Request information",
                    JOptionPane.QUESTION_MESSAGE);

            if (name == null) {
                System.exit(0);
            } else if (name.equals("")) {
                JOptionPane.showMessageDialog(this,
                        "Your name is not valid.", "Invalid input",
                        JOptionPane.ERROR_MESSAGE);
            } else
                break;
        }

        createWindow(name);

        int portNumber = 3500;

        try {
            // Create a client socket
            soc = new Socket(InetAddress.getLocalHost(), portNumber);
            System.out.println("Client socket is created " + soc);
        } catch (IOException ie) {
            ie.printStackTrace();
        }

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!safeClose) {
                    try {
                        OutputStream clientOut = soc.getOutputStream();
                        PrintWriter pw = new PrintWriter(clientOut, true);

                        pw.println("%s: %s".formatted(name, Constant.TERMINATE_MESSAGE));

                        while (!safeClose) {
                            Thread.onSpinWait();
                            //wait for closing socket
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }

                System.exit(0);
            }
        });

    }

    private void createWindow(String name) {
        this.setTitle("Chatter - " + name);

        this.setSize(400, 400);

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

        textField = new JTextField();

        label = new JLabel("Type Here: ");

        container.add(label);
        container.add(textField);

        layout.putConstraint(SpringLayout.WEST, scrollPane, 5, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.EAST, scrollPane, -5, SpringLayout.EAST, container);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 5, SpringLayout.NORTH, container);

        layout.putConstraint(SpringLayout.SOUTH, scrollPane, -5, SpringLayout.NORTH, label);

        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.SOUTH, label, -5, SpringLayout.NORTH, textField);

        layout.putConstraint(SpringLayout.SOUTH, label, -5, SpringLayout.NORTH, textField);

        layout.putConstraint(SpringLayout.SOUTH, textField, -5, SpringLayout.SOUTH, container);
        layout.putConstraint(SpringLayout.WEST, textField, 5, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.EAST, textField, -5, SpringLayout.EAST, container);

        textField.requestFocus();

        textField.addActionListener(e -> {
            try {
                // Create an output stream of the client socket
                OutputStream clientOut = soc.getOutputStream();
                PrintWriter pw = new PrintWriter(clientOut, true);

                pw.println("%s: %s".formatted(name, textField.getText().trim()));
                textField.setText("");
            } catch (IOException io) {
                io.printStackTrace();
            }
        });
    }

    @Override
    public void run() {
        if (soc != null) {
            try {
                // Create an input stream of the client socket
                InputStream clientIn = soc.getInputStream();
                BufferedReader br = new BufferedReader(new
                        InputStreamReader(clientIn));

                // Read data from the input stream of the client socket.
                String msg = "";

                while (!msg.equals(Constant.TERMINATE_MESSAGE)) {
                    msg = br.readLine();
                    if (msg.equals(Constant.SERVER_DOWN) || msg.equals(Constant.EXCEED_MAX_CONNECTION))
                        break;
                    textArea.append("%s\n".formatted(msg));
                }

                if (msg.equals(Constant.SERVER_DOWN)) {
                    textArea.append("Server is down now. Please close the app and try again later.");

                    OutputStream clientOut = soc.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);

                    pw.println("%s: %s".formatted(name, Constant.SERVER_DOWN));
                } else if (msg.equals(Constant.EXCEED_MAX_CONNECTION)) {
                    textArea.append("Server refuses to connect because the maximum of allowed connections has been reached.");

                    OutputStream clientOut = soc.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);

                    pw.println("%s: %s".formatted(name, Constant.EXCEED_MAX_CONNECTION));
                }

                soc.close();
                br.close();

                //Notify that the socket has been closed
                safeClose = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatBox c = new ChatBox();
            c.setVisible(true);
            Thread a = new Thread(c);
            a.start();
        });
    }
}
