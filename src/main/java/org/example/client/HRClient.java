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
    private static final int SERVER_PORT = 5434;

    private JTextField idField;
    private JTextField nameField;
    private JTextField positionField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton displayButton;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public HRClient() {
        super("HR Client");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 250);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Position:"));
        positionField = new JTextField();
        panel.add(positionField);

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String payload = createPayload();
                if (payload != null) {
                    sendRequest("create", payload);
                }
            }
        });
        panel.add(addButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String payload = createIdPayload();
                if (payload != null) {
                    sendRequest("delete", payload);
                }
            }
        });
        panel.add(deleteButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String payload = createPayload();
                if (payload != null) {
                    sendRequest("update", payload);
                }
            }
        });
        panel.add(updateButton);

        displayButton = new JButton("Display All");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendRequest("read", "");
            }
        });
        panel.add(displayButton);

        add(panel);
        setVisible(true);

        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            idField.setEnabled(true);

            System.out.println("Connected to server");

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not connect to server", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void sendRequest(String type, String payload) {
        try {
            if (out != null && in != null) {
                String request = "{\"type\": \"" + type + "\", \"payload\": " + payload + "}";
                out.println(request);
                System.out.println("Sent request to server: " + request);

                StringBuilder responseBuilder = new StringBuilder();
                String response;
                while ((response = in.readLine()) != null) {
                    responseBuilder.append(response);
                    if (!in.ready()) {
                        break;
                    }
                }
                System.out.println("Server response: " + responseBuilder.toString());
                displayResponse(responseBuilder.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Not connected to server", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error receiving response from server", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String createPayload() {
        String idText = idField.getText().trim();
        String name = nameField.getText();
        String position = positionField.getText();

        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            int id = Integer.parseInt(idText);
            return "{\"id\": " + id + ", \"name\": \"" + name + "\", \"position\": \"" + position + "\"}";
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private String createIdPayload() {
        String idText = idField.getText().trim();

        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            int id = Integer.parseInt(idText);
            return "{\"id\": " + id + "}";
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void displayResponse(String response) {
        JOptionPane.showMessageDialog(this, response, "Server Response", JOptionPane.INFORMATION_MESSAGE);
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
