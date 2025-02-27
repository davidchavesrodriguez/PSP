import com.jcraft.jsch.*;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class DownloadImages {
    private static final String PICSUM_URL = "https://picsum.photos/200/300";
    private static final String LOCAL_DIR = "./images";
    private static final String SFTP_HOST = "10.26.0.31";
    private static final int SFTP_PORT = 60000;
    private static final String SFTP_USER = "tester";
    private static final String PRIVATE_KEY_PATH = "";
    private static final String REMOTE_DIR = "";

    public static void main(String[] args) {
        try {
            Files.createDirectories(Paths.get(LOCAL_DIR));
            ExecutorService executor = Executors.newFixedThreadPool(5);
            List<Future<Path>> futures = new CopyOnWriteArrayList<>();

            for (int i = 0; i < 5; i++) {
                futures.add(executor.submit(DownloadImages::downloadImage));
            }

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);

            for (Future<Path> future : futures) {
                Path imagePath = future.get();
                uploadToSFTP(imagePath);
                Files.deleteIfExists(imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Path downloadImage() {
        try {
            String imageUrl = PICSUM_URL + "?random=" + new Random().nextInt(1000);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(imageUrl)).build();
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            Path imagePath = Paths.get(LOCAL_DIR, "image_" + System.nanoTime() + ".jpg");
            Files.write(imagePath, response.body());
            return imagePath;
        } catch (Exception e) {
            throw new RuntimeException("Error downloading image", e);
        }
    }

    private static void uploadToSFTP(Path filePath) {
        JSch jsch = new JSch();
        Session session = null;
        try {
            jsch.addIdentity(PRIVATE_KEY_PATH);
            session = jsch.getSession(SFTP_USER, SFTP_HOST, SFTP_PORT);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            sftpChannel.put(filePath.toString(), REMOTE_DIR + filePath.getFileName());

            sftpChannel.disconnect();
            session.disconnect();
        } catch (SftpException | JSchException e) {
            e.printStackTrace();
        }
    }
}