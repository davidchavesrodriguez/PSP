package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class VowelCounter {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Usage: java VowelCounter <vowel> <filePath>");
            return;
        }

        String vowel = args[0];
        String filePath = args[1];

        int count = countVowels(vowel, filePath);

        System.out.println(count);
    }

    public static int countVowels(String vowel, String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        int count = 0;

        for (String line : lines) {
            for (char c : line.toCharArray()) {
                if (c == vowel.charAt(0) || c == Character.toLowerCase(vowel.charAt(0))) {
                    count++;
                }
            }
        }

        return count;
    }
}
