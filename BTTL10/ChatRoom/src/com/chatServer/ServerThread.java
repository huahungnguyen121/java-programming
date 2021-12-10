package com.chatServer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import com.Constant;

import javax.swing.*;

public class ServerThread extends Thread {
    static ArrayList<Socket> clientSockets = new ArrayList<>();
    static int MAX_CONNECTION;
    static JTextArea textArea;
    Socket soc;
    InputStream clientIn = null;
    String msgFromClient = null;
    BufferedReader br = null;

    ServerThread(Socket s, JTextArea txt, int max, String name) {
        super(name);
        textArea = txt;
        MAX_CONNECTION = max;
        soc = s;

        try {
            clientIn = soc.getInputStream();
            br = new BufferedReader(new InputStreamReader(clientIn));
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        clientSockets.add(soc);
        if (clientSockets.size() > MAX_CONNECTION) {
            try {
                OutputStream clientOut = soc.getOutputStream();
                PrintWriter pw = new PrintWriter(clientOut, true);
                pw.println(Constant.EXCEED_MAX_CONNECTION);
                textArea.append("REFUSE - MAX CONNECTION REACHED: Client host = " + soc.getInetAddress().getHostAddress() + " Client port = " + soc.getPort() + "\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else
            textArea.append("ACCEPT: Client host = " + soc.getInetAddress().getHostAddress() + " Client port = " + soc.getPort() + "\n");
    }

    public static boolean closeAll() {
        try {
            for (Socket clientSocket : clientSockets) {
                OutputStream clientOut = clientSocket.getOutputStream();
                PrintWriter pw = new PrintWriter(clientOut, true);
                pw.println(Constant.SERVER_DOWN);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void run() {
        if (soc != null) {
            while (true) {
                try {
                    msgFromClient = br.readLine();

                    textArea.append("Message received from client => " + msgFromClient + "\n");

                    String[] splitter = msgFromClient.split(": ");

                    // Send response to the client
                    if (msgFromClient != null && !splitter[1].equals(Constant.TERMINATE_MESSAGE) && !splitter[1].equals(Constant.EXCEED_MAX_CONNECTION)) {
                        for (Socket clientSocket : clientSockets) {
                            OutputStream clientOut = clientSocket.getOutputStream();
                            PrintWriter pw = new PrintWriter(clientOut, true);
                            pw.println(msgFromClient);
                        }
                    }

                    // Close sockets
                    if (msgFromClient != null && (splitter[1].equals(Constant.TERMINATE_MESSAGE) || splitter[1].equals(Constant.SERVER_DOWN) || splitter[1].equals(Constant.EXCEED_MAX_CONNECTION))) {
                        textArea.append("Close socket: " + soc + "\n");
                        clientSockets.remove(soc);
                        if (splitter[1].equals(Constant.TERMINATE_MESSAGE)) {
                            for (Socket clientSocket : clientSockets) {
                                OutputStream clientOut = clientSocket.getOutputStream();
                                PrintWriter pw = new PrintWriter(clientOut, true);
                                pw.println("%s left chat room.".formatted(splitter[0]));
                            }

                            //Send terminate command back to client
                            OutputStream clientOut = soc.getOutputStream();
                            PrintWriter pw = new PrintWriter(clientOut, true);
                            pw.println(Constant.TERMINATE_MESSAGE);
                        }
                        soc.close();
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
