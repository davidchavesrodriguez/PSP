package org.example.threadPool;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DownloadFiles implements Callable<Void> {
    private final URL url;
    private final Path outputFile;

    public DownloadFiles(URL url, Path outputFile) {
        this.url = url;
        this.outputFile = outputFile;
    }

    @Override
    public Void call() {
        try (InputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(outputFile.toFile())) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("Downloaded: " + outputFile);
        } catch (IOException e) {
            System.err.println("Failed to download from " + url + ": " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        final int THREAD_NUM = 10;
        Path urlFilePath = Paths.get("src/main/resources/animal-urls.txt");
        Path outputDir = Paths.get("src/main/resources/animalResults");

        // Ensure output directory exists
        try {
            Files.createDirectories(outputDir);
        } catch (IOException e) {
            System.err.println("Could not create output directory: " + e.getMessage());
            return;
        }

        // Create thread pool
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_NUM);
        List<Future<Void>> futures = new ArrayList<>();

        // Read URLs from file and create download tasks
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(urlFilePath.toFile()));
            String line;
            int lineNum = 1; // Track line number for file naming

            while ((line = reader.readLine()) != null) {
                try {
                    URL url = new URL(line);
                    Path outputFile = outputDir.resolve("animal" + lineNum + ".jpg");
                    DownloadFiles task = new DownloadFiles(url, outputFile);
                    futures.add(pool.submit(task));
                    lineNum++;
                } catch (IOException e) {
                    System.err.println("Invalid URL on line " + lineNum + ": " + line);
                    lineNum++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading URLs file: " + e.getMessage());
        } finally {
            // Close the reader in the finally block to ensure it's always closed
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error closing reader: " + e.getMessage());
                }
            }
        }

        // Wait for all tasks to complete
        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                System.err.println("Error in download task: " + e.getMessage());
            }
        }

        // Shutdown the pool
        pool.shutdown();
        System.out.println("All files downloaded.");
    }
}
