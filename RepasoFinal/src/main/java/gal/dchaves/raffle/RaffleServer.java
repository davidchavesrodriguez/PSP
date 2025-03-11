package gal.dchaves.raffle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class RaffleServer {
    private static final int SERVER_PORT = 55555;
    private static final SharedRaffle sharedRaffle = new SharedRaffle();
    private static EmailService emailService;

    public static void main(String[] args) throws FileNotFoundException {

        try (InputStream input = new FileInputStream("src/main/resources/gmail.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            emailService = new EmailService(
                    properties.getProperty("mail.smtp.host"),
                    properties.getProperty("mail.smtp.port"),
                    properties.getProperty("mail.smtp.user"),
                    properties.getProperty("mail.smtp.password")
            );

            try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
                System.out.println("Raffle Server started at " + SERVER_PORT);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());

                    // Thread for a client
                    RaffleServerWorker worker = new RaffleServerWorker(clientSocket, sharedRaffle, emailService);
                    new Thread(worker).start();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
