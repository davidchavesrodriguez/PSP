package wikipediaapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WikipediaAPI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter country code (e.g., US, ES): ");
        String countryCode = scanner.nextLine().trim();

        System.out.print("Enter date (YYYY/MM/DD): ");
        String date = scanner.nextLine().trim();

        scanner.close();

        String apiUrl = String.format(
                "https://wikimedia.org/api/rest_v1/metrics/pageviews/top-per-country/%s/all-access/%s",
                countryCode, date
        );

        System.out.println("Fetching data from " + apiUrl);

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.lines().collect(Collectors.joining());
                reader.close();

                // Imprime la respuesta para depuración
                // System.out.println("Response from API: " + response);

                JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();

                // Accede al primer elemento de 'items' y luego a 'articles'
                JsonArray articles = jsonResponse.getAsJsonArray("items")
                        .get(0).getAsJsonObject()
                        .getAsJsonArray("articles");

                // Verifica si los artículos se extrajeron correctamente
                System.out.println("Articles: " + articles);

                String filename = String.format("wiki_top_%s_%s.json", countryCode, date.replace("/", "-"));
                saveJsonToFile(articles, filename);

                uploadToFTP(filename);
            } else {
                System.out.println("Failed to fetch data. Response code: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void saveJsonToFile(JsonArray json, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(json, writer);
            System.out.println("Data saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void uploadToFTP(String filename) {
        String server = "10.26.0.31";
        int port = 21;
        String user = "user";
        String pass = "user";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            File file = new File(filename);
            try (FileInputStream fis = new FileInputStream(file)) {
                boolean uploaded = ftpClient.storeFile(filename, fis);
                if (uploaded) {
                    System.out.println("File uploaded successfully.");
                } else {
                    System.out.println("Failed to upload file.");
                }
            }
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
