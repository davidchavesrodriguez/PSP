package polybiuscipher;

import java.io.*;
import java.net.*;

public class PolybiusClient {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PORT = 5555;

        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to: " + HOST + ":" + PORT);

            String message;
            do {
                System.out.print("Send a message (type 'exit' to disconnect): ");
                message = userInput.readLine();

                out.println(message);

                if (!message.equalsIgnoreCase("exit")) {
                    String encryptedMessage = in.readLine();
                    System.out.println("Encrypted message: " + encryptedMessage);
                }
            } while (!message.equalsIgnoreCase("exit"));

            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
