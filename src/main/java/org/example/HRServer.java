package org.example;
import org.example.clienthandler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HRServer {
    private static final int PORT = 12345;
    private static final int MAX_THREADS = 10; // Maksymalna liczba równocześnie obsługiwanych klientów

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Serwer HR uruchomiony na porcie " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nowe połączenie: " + clientSocket);

                // Tworzymy nowy wątek dla obsługi klienta
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                executorService.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            executorService.shutdown();
        }
    }
}
