package org.example.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HRClient extends JFrame {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8083;

    private JTextField idField;
    private JTextField nameField;
    private JTextField positionField;
    private JButton sendButton;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public HRClient() {
        super("HR Client");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Position:"));
        positionField = new JTextField();
        panel.add(positionField);

        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendRequest("create", createPayload());
            }
        });
        panel.add(sendButton);

        add(panel);
        setVisible(true);

        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(String type, String payload) {
        if (out != null) {
            String request = "{\"type\": \"" + type + "\", \"payload\": \"" + payload + "\"}";
            out.println(request);
            System.out.println("Sent request to server: " + request);

            try {
                String response = in.readLine();
                System.out.println("Server response: " + response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String createPayload() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String position = positionField.getText();
        return "{\"id\": " + id + ", \"name\": \"" + name + "\", \"position\": \"" + position + "\"}";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HRClient();
            }
        });
    }
}
