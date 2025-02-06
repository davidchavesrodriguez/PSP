package fundraising.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class FundraisingClientUDP {
    private static final int PORT = 55555;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("Enter command: (ADD <amount>, SHOW, QUIT)");
                String command = userInput.readLine();

                byte[] buffer = command.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, serverAddress, PORT);
                socket.send(sendPacket);

                if (command.equalsIgnoreCase("QUIT")) {
                    break;
                }

                byte[] receivedBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receivedBuffer, receivedBuffer.length);
                socket.receive(receivePacket);

                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server response: " + response);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
