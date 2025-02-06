package fundraising.tcp;

import java.io.*;
import java.net.*;

public class FundraisingServerTCP {
    private static int totalFunds = 0;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(55555)) {
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new FundraisingServerTCPWorker(clientSocket, totalFunds).start();
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