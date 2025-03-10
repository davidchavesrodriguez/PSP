package gal.dchaves.p3_07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RandomWordServerWorker implements Runnable {
    private Socket clientSocket;

    public RandomWordServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Leer comando cliente
            String command = in.readLine();
            String response;

            // Procesar comando
            if (command != null && command.startsWith("WORD")) {
                int length = 5;
                String[] commandParts = command.split(" ");
                if (commandParts.length > 1) {
                    try {
                        length = Integer.parseInt(commandParts[1]);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                }

                // Obtener a palabra
                String randomWord = getRandomWord(length);
                response = "Random Word gotten: " + randomWord;
            } else {
                response = "Check your command";
            }

            out.println(response);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRandomWord(int length) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://random-word-api.herokuapp.com/word?length=" + length)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
