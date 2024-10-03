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
            pb.command("cmd.exe", "/c", "set /a RESULT=%NUMBER1%+%NUMBER2%");

            Process process = pb.start();
            process.waitFor();
            process.getInputStream().transferTo(System.out);

        } catch (NumberFormatException e) {
            System.err.println("Error converting environment variables to numbers: " + e.getMessage());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
