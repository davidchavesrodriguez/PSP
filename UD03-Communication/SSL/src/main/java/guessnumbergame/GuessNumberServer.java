package guessnumbergame;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GuessNumberServer {
    public static void main(String[] args) throws IOException {
        // Initialize ServerSocket
        int port = 59000;
        try (ServerSocket listener = new ServerSocket(port)) {
            System.out.println("Running on port " + port);

            while (true) {
                Socket clientSocket = listener.accept();
                System.out.println("Client accepted");

                new Thread(new ClientHandler(clientSocket)).start();
            }
        }
    }
}