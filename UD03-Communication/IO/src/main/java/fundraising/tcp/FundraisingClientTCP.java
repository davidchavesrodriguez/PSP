package fundraising.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class FundraisingClientTCP {
    private static final String HOST = "localhost";
    private static final int PORT = 55555;

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))
        ) {
            while (true) {
                System.out.println("Enter command: (ADD <amount>, SHOW, QUIT)");
                String command = userInput.readLine();
                out.println(command);

                if (command.equalsIgnoreCase("QUIT")) {
                    break;
                }

                String response = in.readLine();
                System.out.println("Server response: " + response);
            }
        }
    }
}
