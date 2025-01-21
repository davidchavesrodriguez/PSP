package polybiuscipher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class PolybiusServer {
    private static final Map<Character, String> polybiusMap = new HashMap<>();

    static {
        char[][] table = {
                {'A', 'B', 'C', 'D', 'E', 'F'},
                {'G', 'H', 'I', 'J', 'K', 'L'},
                {'M', 'N', 'O', 'P', 'Q', 'R'},
                {'S', 'T', 'U', 'V', 'W', 'X'},
                {'Y', 'Z', '0', '1', '2', '3'},
                {'4', '5', '6', '7', '8', '9'}
        };

        // Coordenadas del Polybius
        for (int row = 0; row < table.length; row++) {
            for (int col = 0; col < table[row].length; col++) {
                char character = table[row][col];
                String coordinates = (row + 1) + "" + (col + 1);
                polybiusMap.put(character, coordinates);
            }
        }
    }

    // Método de encriptación
    private static String encrypt(String message) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : message.toUpperCase().toCharArray()) {
            if (polybiusMap.containsKey(c)) {
                encrypted.append(polybiusMap.get(c)).append(" ");
            } else if (c == ' ') {
                encrypted.append("00 "); // Codificar espacios
            }
        }
        return encrypted.toString().trim();
    }

    public static void main(String[] args) throws IOException {
        final int PORT = 5555;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server listening on " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected client: " + clientSocket.getInetAddress());

                // Comunicación con el cliente
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String message;
                    while ((message = in.readLine()) != null) {
                        if (message.equalsIgnoreCase("exit")) {
                            System.out.println("Client disconnected.");
                            break; // Salir del bucle para cerrar la conexión
                        }

                        System.out.println("Received: " + message);
                        String encryptedMessage = encrypt(message);
                        out.println(encryptedMessage); // Enviar el mensaje cifrado
                        System.out.println("Encrypted message: " + encryptedMessage);
                    }
                }
            }
        }
    }
}
