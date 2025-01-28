package guessnumbergame;

import basic.SSLServer;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;

public class GuessNumberServer {
    public static void main(String[] args) throws IOException {

        // Specify properties by code
        System.setProperty("javax.net.ssl.keyStore", "src/main/resources/ServerKeys.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "12345678");

        // Initialize SSL ServerSocket
        int port= 66000;
        SSLServerSocketFactory sslServerSocketFactory= (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket sslServerSocket= (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);

        System.out.println("Listening on port " + port);

        while (true){
            // Accept client connections
            SSLSocket clientSocket= (SSLSocket) sslServerSocket.accept();
            System.out.println("Client connected");

            // Create thread for handling the client
            new ClientHandler(clientSocket).start();
        }
    }
}