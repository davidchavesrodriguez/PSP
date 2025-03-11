package gal.dchaves.raffle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RaffleServerWorker implements Runnable {
    private final Socket clientSocket;
    private final SharedRaffle sharedRaffle;
    private final EmailService emailService;

    public RaffleServerWorker(Socket clientSocket, SharedRaffle sharedRaffle, EmailService emailService) {
        this.clientSocket = clientSocket;
        this.sharedRaffle = sharedRaffle;
        this.emailService = emailService;
    }


    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            System.out.println("Thread working for " + clientSocket.getPort());

            // Read client data
            String name = in.readLine();
            String email = in.readLine();

            // Register participant
            Participant participant = new Participant(name, email);
            sharedRaffle.addParticipant(participant);

            // Pick a winner
            Participant winner = sharedRaffle.selectWinner();
            if (winner != null) {
                emailService.sendEmail(winner.getEmail(), "Winner", "You won!");
            }

            // Send email to everyone
            for (Participant participantRecipient : sharedRaffle.getParticipants()) {
                emailService.sendEmail(participantRecipient.getEmail(), "Winner Results", "The winner is " + winner.getName());
            }

            // Response to client
            out.println("Register done!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
