package squarenumber;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SquareServer {
    private static final int PORT = 55555;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from: " + clientSocket.getInetAddress());

                new SquareServerWorker(clientSocket).start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
