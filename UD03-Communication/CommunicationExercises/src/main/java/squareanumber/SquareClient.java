package squareanumber;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SquareClient {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Pass the server IP as an argument");
            return;
        }

        // Conexión al servidor
        try (var socket = new Socket(args[0], 59999);
             var in = new Scanner(socket.getInputStream());
             var out = new PrintWriter(socket.getOutputStream(), true);
             var console = new Scanner(System.in)) {

            System.out.println("Connected to the server.");

            // Menú interactivo
            String input;
            do {
                System.out.println("Enter a number to square (or 'exit' to quit):");
                input = console.nextLine();

                if (!input.equalsIgnoreCase("exit")) {
                    // Enviar número al servidor
                    out.println(input);

                    // Recibir y mostrar respuesta del servidor
                    if (in.hasNextLine()) {
                        System.out.println("Response from server: " + in.nextLine());
                    }
                }
            } while (!input.equalsIgnoreCase("exit"));

            System.out.println("Disconnected from the server.");
        }
    }
}
