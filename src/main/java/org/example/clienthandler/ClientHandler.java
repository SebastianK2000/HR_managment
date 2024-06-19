package org.example.clienthandler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.example.service.EmployeeService;
import org.example.model.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final EmployeeService employeeService;
    private final Gson gson;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.employeeService = new EmployeeService();
        this.gson = new Gson();
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Otrzymano żądanie od klienta: " + inputLine);

                // Sprawdzanie, czy żądanie zawiera dane JSON
                if (isJsonRequest(inputLine)) {
                    // Przetwarzanie żądania
                    String response = processRequest(inputLine);

                    // Wysłanie odpowiedzi do klienta
                    out.println(response);
                } else {
                    System.out.println("Invalid JSON format received: " + inputLine);
                    out.println("Invalid request format"); // Możesz wysłać odpowiedź o niepoprawnym formacie
                }
            }
        } catch (IOException e) {
            System.err.println("Błąd obsługi klienta: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Błąd zamknięcia połączenia: " + e.getMessage());
            }
        }
    }

    private boolean isJsonRequest(String inputLine) {
        // Tutaj możesz zaimplementować logikę sprawdzającą, czy inputLine zawiera poprawny JSON
        try {
            gson.fromJson(inputLine, Object.class);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }

    private String processRequest(String request) {
        String response;

        try {
            Request req = gson.fromJson(request, Request.class);

            if (req == null) {
                response = "Invalid request format";
                return response;
            }

            switch (req.getType()) {
                case "create":
                    Employee newEmployee = gson.fromJson(req.getPayload(), Employee.class);
                    employeeService.saveEmployee(newEmployee);
                    response = "Employee created";
                    break;
                case "read":
                    int id = Integer.parseInt(req.getPayload());
                    Employee employee = employeeService.getEmployeeById(id);
                    if (employee != null) {
                        response = gson.toJson(employee);
                    } else {
                        response = "Employee not found";
                    }
                    break;
                case "update":
                    Employee updatedEmployee = gson.fromJson(req.getPayload(), Employee.class);
                    employeeService.updateEmployee(updatedEmployee);
                    response = "Employee updated";
                    break;
                case "delete":
                    int deleteId = Integer.parseInt(req.getPayload());
                    employeeService.deleteEmployee(deleteId);
                    response = "Employee deleted";
                    break;
                default:
                    response = "Invalid request type";
            }
        } catch (JsonSyntaxException e) {
            response = "Invalid JSON format";
            System.err.println("Error processing JSON: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            response = "Invalid payload format";
            System.err.println("Error parsing payload: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            response = "Internal server error";
            System.err.println("Internal server error: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    private static class Request {
        private String type;
        private String payload;

        public String getType() {
            return type;
        }

        public String getPayload() {
            return payload;
        }
    }
}
