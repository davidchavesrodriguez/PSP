package org.example;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Tracert {
    public static void main(String[] args) {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "tracert iessanclemente.net");
        processBuilder.redirectOutput(new File("src/main/resources/outputTracert.txt"));

        try {
            Process process = processBuilder.start();
            process.waitFor(1000, TimeUnit.MILLISECONDS);

            process.destroy();
            System.out.println("Process destroyed after a second");

            File outputFile = new File("src/main/resources/outputTracert.txt");
            if (outputFile.exists()) {
                System.out.println("File outputTracert.txt was created");
            } else {
                System.out.println("File could not be created");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
