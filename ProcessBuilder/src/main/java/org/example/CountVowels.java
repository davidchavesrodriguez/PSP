package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CountVowels {
    public static void main(String[] args) throws IOException, InterruptedException {

        String filePath = "src/main/resources/fileData.txt";
        String[] vowels = {"A", "E", "I", "O", "U"};

        // Main Process
        ProcessBuilder pb;
        for (String vowel : vowels) {
            pb = new ProcessBuilder("java", "-cp", "target/classes", "org.example.VowelCounter", vowel, filePath);
            pb.redirectOutput(new File("src/main/resources/counted" + vowel + ".txt"));
            pb.start();
        }
        Thread.sleep(1000);

        int totalVowels = 0;
        for (String vowel : vowels) {
            File outputFile = new File("src/main/resources/counted" + vowel + ".txt");
            if (outputFile.exists()) {
                List<String> lines = Files.readAllLines(Paths.get(outputFile.getPath()));
                if (!lines.isEmpty()) {
                    int count = Integer.parseInt(lines.getFirst());
                    System.out.println("Number of " + vowel + ": " + count);
                    totalVowels += count;
                }
            }
        }
        System.out.println("Total number of vowels: " + totalVowels);
    }
}
