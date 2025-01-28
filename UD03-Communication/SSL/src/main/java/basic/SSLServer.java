package basic;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;

public class SSLServer {
    public static void main(String[] args) throws IOException {
        SSLSocket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        //Properties can be specified by code
        System.setProperty("javax.net.ssl.keyStore", "src/main/resources/ServerKeys.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "12345678");

        //Initialisation of the SSL ServerSocket
        int port = 60000;
        SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory)
                SSLServerSocketFactory.getDefault();
        SSLServerSocket SSLserver = (SSLServerSocket)
                sslServerSocketFactory.createServerSocket(port);
        for (int i = 1; i < 5; i++) {
            System.out.println("Waiting for the client " + i);
            //A client connection is expected with accept
            clientSocket = (SSLSocket) SSLserver.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new
                    InputStreamReader(clientSocket.getInputStream()));
            //Message received from client
            System.out.println("Receiving from the client: " + i + " \n\t" +
                    in.readLine());
            //The Server responds with a greeting
            out.println("Greetings to the client from the server");
        }
        //Closing streams and sockets
        in.close();
        out.close();
        clientSocket.close();
        SSLserver.close();
    }
}