package guessnumbergame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int numberToGuess;
    private int attemptsLeft;
    private boolean gameStarted = false;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Mensaje inicial al cliente
            out.println("10 Number game server ready");

            String command;
            while ((command = in.readLine()) != null) {
                System.out.println("Received: " + command);
                processCommand(command);
            }

        } catch (IOException e) {
            System.out.println("Client disconnected: " + e.getMessage());
        }
    }

    private void processCommand(String command) {
        String[] parts = command.split(" ");

        switch (parts[0]) {
            case "NEW":
                if (parts.length == 2) {
                    startNewGame(parts[1]);
                } else {
                    out.println("90 UNKNOWN");
                }
                break;

            case "NUM":
                if (parts.length == 2 && gameStarted) {
                    checkGuess(parts[1]);
                } else {
                    out.println("80 ERR");
                }
                break;

            case "HELP":
                out.println("40 INFO NEW <numTries>, NUM <number>, HELP, QUIT");
                break;

            case "QUIT":
                out.println("11 BYE");
                closeConnection();
                break;

            default:
                out.println("90 UNKNOWN");
        }
    }

    private void startNewGame(String triesStr) {
        try {
            attemptsLeft = Integer.parseInt(triesStr);
            numberToGuess = new Random().nextInt(100) + 1;
            gameStarted = true;
            out.println("20 PLAY " + attemptsLeft);
        } catch (NumberFormatException e) {
            out.println("90 UNKNOWN");
        }
    }

    private void checkGuess(String guessStr) {
        try {
            int guess = Integer.parseInt(guessStr);
            attemptsLeft--;

            if (guess == numberToGuess) {
                out.println("50 WIN");
                gameStarted = false;
            } else if (attemptsLeft == 0) {
                out.println("70 LOSE NUM " + numberToGuess);
                gameStarted = false;
            } else if (guess < numberToGuess) {
                out.println("25 LOW " + attemptsLeft);
            } else {
                out.println("35 HIGH " + attemptsLeft);
            }
        } catch (NumberFormatException e) {
            out.println("90 UNKNOWN");
        }
    }

    private void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
