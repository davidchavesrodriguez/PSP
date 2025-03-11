package gal.dchaves.raffle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class RaffleClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 55555;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected to Raffle Server!");

            // Ask for data
            System.out.println("Name: ");
            String name = scanner.nextLine();
            System.out.println("Email: ");
            String email = scanner.nextLine();

            // Send data to server
            out.println(name);
            out.println(email);

            // Get server response
            String response = in.readLine();
            System.out.println("REsponse: " + response);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
