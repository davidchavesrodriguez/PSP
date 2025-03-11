package gal.dchaves.p4_10;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jcraft.jsch.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImagesSFTP {
    private static final String PICSUM_API_URL = "https://picsum.photos/v2/list?page=%d&limit=%d";
    private static final String SFTP_HOST = "192.168.1.228";
    private static final int SFTP_PORT = 22;
    private static final String SFTP_USER = "tester";
    private static final String PRIVATE_KEY_PATH = "C:\\Users\\19dch\\OneDrive\\Escritorio\\private.ppk";
    private static final String REMOTE_DIR = "";
    private static final String LOCAL_DIR = "src/main/resources/images";

    public static void main(String[] args) {
        new File(LOCAL_DIR).mkdir();

        List<String> imageUrls = fetchRandomImageUrls(2, 5);
        if (imageUrls.isEmpty()) {
            System.out.println("There was an error obtaining the urls...");
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(imageUrls.size());
        List<String> downloadedFiles = new ArrayList<>();

        for (String imageUrl : imageUrls) {
            executor.execute(() -> {
                String localFilePath = downloadImage(imageUrl);
                if (localFilePath != null) {
                    downloadedFiles.add(localFilePath);
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {

        }

        uploadFilesToSFTP(downloadedFiles);
        for (String filePath : downloadedFiles) {
            new File(filePath).delete();
            System.out.println("Eliminado: " + filePath);
        }
    }

    private static List<String> fetchRandomImageUrls(int page, int limit) {
        List<String> imageUrls = new ArrayList<>();
        try {
            String apiUrl = String.format(PICSUM_API_URL, page, limit);
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parsear la respuesta JSON
            JsonArray jsonArray = JsonParser.parseString(response.toString()).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                String imageUrl = jsonObject.get("download_url").getAsString();
                imageUrls.add(imageUrl);
            }
        } catch (IOException e) {
            System.err.println("Error al obtener las URLs de las imágenes: " + e.getMessage());
        }
        return imageUrls;
    }

    private static String downloadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
            String localFilePath = LOCAL_DIR + File.separator + fileName;

            try (InputStream in = url.openStream();
                 FileOutputStream out = new FileOutputStream(localFilePath)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            System.out.println("Imagen descargada: " + localFilePath);
            return localFilePath;
        } catch (IOException e) {
            System.err.println("Error al descargar la imagen: " + e.getMessage());
            return null;
        }
    }

    private static void uploadFilesToSFTP(List<String> filePaths) {
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            // Configurar la clave privada
            jsch.addIdentity(PRIVATE_KEY_PATH);

            // Conectar al servidor SFTP
            session = jsch.getSession(SFTP_USER, SFTP_HOST, SFTP_PORT);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            // Abrir canal SFTP
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // Subir archivos
            for (String filePath : filePaths) {
                File file = new File(filePath);
                channelSftp.put(new FileInputStream(file), REMOTE_DIR + "/" + file.getName());
                System.out.println("Archivo subido: " + file.getName());
            }
        } catch (SftpException | IOException | JSchException e) {
            System.err.println("Error al subir archivos al SFTP: " + e.getMessage());
        } finally {
            // Cerrar conexiones
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}
