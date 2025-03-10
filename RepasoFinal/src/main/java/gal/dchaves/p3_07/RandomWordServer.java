package gal.dchaves.p3_07;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RandomWordServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(55555)) {
            System.out.println("Server is running...");

            while (true) {
                // Aceptar conexións cliente
                Socket clientSocket = serverSocket.accept();
                System.out.println("New cliente connected!: " + clientSocket.getInetAddress());

                // Crear hilo para manexar a conexion
                RandomWordServerWorker worker = new RandomWordServerWorker(clientSocket);
                new Thread(worker).start();
            }
        }
    }
}
