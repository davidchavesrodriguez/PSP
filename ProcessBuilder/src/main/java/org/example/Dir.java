package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Dir {
    public static void main(String[] args) {
        // Create a ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder();

        // Checks C:/ path
        executeDirCommand(pb, "C:/");

        // Asks user for a path
        try (Scanner scanner= new Scanner(System.in)) {
            System.out.println("Give me a path to check: ");
            String pathGiven = scanner.nextLine();

            // Checks user´s path
            executeDirCommand(pb, pathGiven);
        }
    }

    private static void executeDirCommand(ProcessBuilder pb, String path) {
        pb.directory(new File(path));
        pb.command("cmd.exe", "/c", "dir");

        try {
            Process process = pb.start();
            process.waitFor();
            process.getInputStream().transferTo(System.out);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
