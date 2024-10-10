package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CountVowels {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Check if there are arguments
        if (args.length == 2) {
            // If there are two arguments, count vowels
            String vowel = args[0];
            String filePath = args[1];
            countVowels(vowel, filePath);
            return; // Terminate the child process
        }

        // Code for the parent process
        String filePath = "src/main/resources/fileData.txt";
        String[] vowels = {"A", "E", "I", "O", "U"};

        // Launch a process to count each vowel
        for (String vowel : vowels) {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", "target/classes", "org.example.CountVowels", vowel, filePath);
            pb.redirectOutput(new File("src/main/resources/counted" + vowel + ".txt"));
            pb.start();
        }

        // Wait for a moment for the processes to finish
        Thread.sleep(1000);

        // Collect the results
        int totalVowels = 0;
        for (String vowel : vowels) {
            File outputFile = new File("src/main/resources/counted" + vowel + ".txt");
            if (outputFile.exists()) {
                List<String> lines = Files.readAllLines(Paths.get(outputFile.getPath()));
                if (!lines.isEmpty()) {
                    int count = Integer.parseInt(lines.get(0));
                    System.out.println("Number of " + vowel + ": " + count);
                    totalVowels += count;
                }
            }
        }

        // Display the total number of vowels
        System.out.println("Total number of vowels: " + totalVowels);
    }

    // Method to count the vowels when executed with parameters
    public static void countVowels(String vowel, String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        int count = 0;

        // Count vowels
        for (String line : lines) {
            for (char c : line.toCharArray()) {
                if (c == vowel.charAt(0) || c == Character.toLowerCase(vowel.charAt(0))) {
                    count++;
                }
            }
        }

        // Output the result (the process redirects this output to a file)
        System.out.println(count);
    }
}
