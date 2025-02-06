package fundraising.udp;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class FundraisingServerUDPWorker implements Runnable {
    private DatagramSocket socket;
    private DatagramPacket packet;
    private int totalFunds;

    public FundraisingServerUDPWorker(DatagramSocket socket, DatagramPacket packet, int totalFunds) {
        this.socket = socket;
        this.packet = packet;
        this.totalFunds = totalFunds;
    }

    @Override
    public void run() {
        try {
            String command = new String(packet.getData(), 0, packet.getLength());
            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();
            String response;

            if (command.toUpperCase().startsWith("ADD")) {
                try {
                    int amount = Integer.parseInt(command.split(" ")[1]);
                    FundraisingServerUDP.addFunds(amount);
                    response = "Added " + amount + ". Total funds: " + FundraisingServerUDP.getTotalFunds();
                } catch (Exception e) {
                    response = "Invalid request";
                }
            } else if (command.equalsIgnoreCase("SHOW")) {
                response = "Total funds: " + FundraisingServerUDP.getTotalFunds();
            } else if (command.equalsIgnoreCase("QUIT")) {
                response = "Goodbye!";
            } else {
                response = "Invalid request";
            }

            byte[] sendData = response.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            socket.send(sendPacket);

            System.out.println("Client " + clientPort + ": " + command);
            System.out.println("Server response to " + clientPort + ": " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}