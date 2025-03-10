package gal.dchaves.p3_07;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;

public class RandomWordClient {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 55555)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Leer comando usuario
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter command 'WORD <number_letters>': ");
            String command = scanner.nextLine();

            // Enviar comando a servidor
            out.println(command);

            // Recibir resposta servidor
            String response = in.readLine();
            System.out.println("Server response: " + response);
        }
    }
}
