package fundraising.udp;

import java.io.*;
import java.net.*;

public class FundraisingServerUDP {
    public static final int PORT = 55555;
    public static int totalFunds = 0;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Server is running...");

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);

                new FundraisingServerUDPWorker(socket, request, totalFunds).run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addFunds(int amount) {
        totalFunds += amount;
    }

    public static synchronized int getTotalFunds() {
        return totalFunds;
    }
}