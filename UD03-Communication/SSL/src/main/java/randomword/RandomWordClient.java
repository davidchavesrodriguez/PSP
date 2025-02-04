package randomword;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class RandomWordClient {
    public static void main(String[] args) {
        // Server Port
        final int SERVER_PORT = 59999;
        // Buffer to store messages
        byte[] buffer = new byte[1024];

        DatagramSocket socketUDP = null;

        try {
            // Get the location of the server
            InetAddress serverAddress = InetAddress.getByName("localhost");
            // Create UDP socket
            socketUDP = new DatagramSocket();

            // Read user input
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please, enter command (WORD <number_of_letters>): ");
            String command = userInput.readLine();

            // Validate and format the command
            if (!command.startsWith("WORD")) {
                System.out.println("Invalid command. Using default: WORD 5");
                command = "WORD 5";
            } else {
                // Check if the command has a valid number
                String[] parts = command.split(" ");
                if (parts.length < 2 || !parts[1].matches("\\d+")) {
                    System.out.println("Invalid number of letters. Using default: 5");
                    command = "WORD 5";
                }
            }

            // Convert to bytes
            buffer = command.getBytes();

            // Create the datagram
            DatagramPacket question = new DatagramPacket(buffer, buffer.length, serverAddress, SERVER_PORT);

            // Send the datagram
            System.out.println("Sending datagram to server...");
            socketUDP.send(question);

            // Prepare for answer
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);

            // Receiving answer
            System.out.println("Waiting for server response...");
            socketUDP.receive(response);

            // Convert response to string
            String message = new String(response.getData(), 0, response.getLength(), "UTF-8");
            System.out.println("Server response: " + message);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Close socket
            if (socketUDP != null && !socketUDP.isClosed()) {
                socketUDP.close();
                System.out.println("Socket closed.");
            }
        }
    }
}