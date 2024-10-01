package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SetEnvironment {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder();
        Map<String, String> environment = pb.environment();
        environment.put("NUMBER1", "19");
        environment.put("NUMBER2", "77");
        try {
            System.out.println("Current Environment Variables:");
            for (Map.Entry<String, String> entry : environment.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            Process p = new ProcessBuilder("set finalNumber= NUMBER1 + NUMBER2").start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

