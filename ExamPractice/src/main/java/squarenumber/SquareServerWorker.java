package squarenumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SquareServerWorker extends Runnable {
    private Socket clientSocket;

    public SquareServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
