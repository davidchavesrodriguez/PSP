package randomword;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomWordServer {
    public static void main(String[] args) throws SocketException {
        final int SERVER_PORT = 59999;

        try(DatagramSocket socketUDP = new DatagramSocket(SERVER_PORT)){
            System.out.println("Random Word Server started on port: " + SERVER_PORT);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket request= new DatagramPacket(buffer, buffer.length);

                // Wait for client
                socketUDP.receive(request);

                // Handle request in a thread
                new Thread(new RandomWordServerWorker(socketUDP, request)).start();

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}