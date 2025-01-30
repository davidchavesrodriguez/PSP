package guessnumbergame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GuessNumberClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 59000;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Server: " + in.readLine());

            while (true) {
                System.out.print("Enter command: ");
                String command = scanner.nextLine();
                out.println(command);

                String response = in.readLine();
                System.out.println("Server: " + response);

                if (response.startsWith("11 BYE")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}
