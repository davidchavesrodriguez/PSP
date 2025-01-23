package squareanumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SquareServerWorker implements Runnable {
    private final Socket socket;

    public SquareServerWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Connected to a client");
        try (
                var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                var out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String input;
            while ((input = in.readLine()) != null) {
                try {
                    int number = Integer.parseInt(input);
                    int square = number * number;
                    out.println("Square: " + square); // Enviar respuesta al cliente
                } catch (NumberFormatException e) {
                    out.println("Invalid number. Please send an integer.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }

}
