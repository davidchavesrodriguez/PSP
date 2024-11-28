package gal.classexercises.producerconsumer.mailbox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class Producer implements Runnable {
    private Mailbox mailbox;
    private final Random random = new Random();

    public Producer(Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String randomWord = fetchRandomWord();
                mailbox.put(randomWord);
                Thread.sleep(random.nextInt(500, 2000));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Random Word API
    private String fetchRandomWord() {
        String apiUrl = "https://random-word-api.herokuapp.com/word";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String response = reader.readLine();
                return response.substring(2, response.length() - 2);
            }
        } catch (Exception e) {
            System.err.println("Error fetching random word: " + e.getMessage());
            return "ErrorWord";
        }
    }
}
