package org.example.threadPool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class ImageProcessing implements Callable<Void> {
    private final Path inputFile;
    private final Path outputDir;

    public ImageProcessing(Path inputFile, Path outputDir) {
        this.inputFile = inputFile;
        this.outputDir = outputDir;
    }

    @Override
    public Void call() {
        processImage(inputFile, outputDir);
        return null;
    }

    public static void processImage(Path inputFile, Path outputDir) {
        BufferedImage image;

        try {
            // Read the input image
            image = ImageIO.read(inputFile.toFile());
            if (image == null) {
                System.out.println("Image " + inputFile + " could not be processed");
                return;
            }

            int width = image.getWidth();
            int height = image.getHeight();
            int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);

            // Convert each pixel to grayscale
            for (int i = 0; i < pixels.length; i++) {
                int p = pixels[i];
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                int avg = (r + g + b) / 3;
                pixels[i] = (a << 24) | (avg << 16) | (avg << 8) | avg;
            }

            // Set the grayscale pixels back to the image
            image.setRGB(0, 0, width, height, pixels, 0, width);

            String outputFileName = inputFile.getFileName().toString();
            outputFileName = outputFileName.substring(0, outputFileName.lastIndexOf('.')) + "_grey.png";
            File outputFile = new File(outputDir.toFile(), outputFileName);
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image processed to B&W: " + outputFile.getPath());

        } catch (IOException e) {
            throw new RuntimeException("Error processing image " + inputFile, e);
        }
    }

    public static void main(String[] args) {
        final int THREAD_NUM = 5;
        Path inputDir = Paths.get("src/main/resources/originals");
        Path outputDir = Paths.get("src/main/resources/results");

        ExecutorService pool = Executors.newFixedThreadPool(THREAD_NUM);
        List<Future<Void>> futures = new ArrayList<>();

        // Process each file in the input directory
        try (Stream<Path> files = Files.list(inputDir)) {
            files.forEach(file -> {
                ImageProcessing task = new ImageProcessing(file, outputDir);
                futures.add(pool.submit(task));
            });
        } catch (IOException e) {
            System.err.println("Error accessing input directory: " + e.getMessage());
        }

        // Wait for all tasks to complete
        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                System.err.println("Error in processing task: " + e.getMessage());
            }
        }

        // Shut down the thread pool
        pool.shutdown();
        System.out.println("All images processed.");
    }
}
