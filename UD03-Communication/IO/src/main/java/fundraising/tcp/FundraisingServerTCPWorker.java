package fundraising.tcp;

import java.io.*;
import java.net.*;

public class FundraisingServerTCPWorker extends Thread {
    private Socket clientSocket;
    private int totalFunds;

    public FundraisingServerTCPWorker(Socket clientSocket, int totalFunds) {
        this.clientSocket = clientSocket;
        this.totalFunds = totalFunds;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String command;
            while ((command = in.readLine()) != null) {
                String response;

                if (command.startsWith("ADD")) {
                    try {
                        int amount = Integer.parseInt(command.split(" ")[1]);
                        FundraisingServerTCP.addFunds(amount);
                        response = "Added " + amount + ". Total funds: " + FundraisingServerTCP.getTotalFunds();
                    } catch (Exception e) {
                        response = "Invalid request";
                    }
                } else if (command.equalsIgnoreCase("SHOW")) {
                    response = "Total funds: " + FundraisingServerTCP.getTotalFunds();
                } else if (command.equalsIgnoreCase("QUIT")) {
                    response = "Goodbye!";
                    break;
                } else {
                    response = "Invalid request";
                }

                out.println(response);
                System.out.println("Client " + clientSocket.getPort() + ": " + command);
                System.out.println("Server response to " + clientSocket.getPort() + ": " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}