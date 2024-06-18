package org.example.clienthandler;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
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

                // Przetwarzanie żądania (tutaj możesz dodać logikę obsługi)
                String response = processRequest(inputLine);

                // Wysłanie odpowiedzi do klienta
                out.println(response);
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

    private String processRequest(String request) {
        // Tutaj możesz dodać logikę przetwarzania żądania klienta
        // Na przykład odczyt i zapis danych do bazy danych, zgodnie z twoim planem projektu
        // Zwróć odpowiedź do klienta
        return "Odpowiedź na żądanie: " + request;
    }
}
