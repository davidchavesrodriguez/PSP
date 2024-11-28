package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class JavaRuntimeClass2 {
    public static void main(String[] args) throws IOException {
        try {
            Runtime runtime = Runtime.getRuntime();
            Random random = new Random();
            List<Integer> ages = new ArrayList<>();
            int numberOfCases = random.nextInt(1, 11);
            System.out.println("Cases: " + numberOfCases);

            List<String> command = new ArrayList<>();
            command.add("java");
            command.add("-cp");
            command.add("src/main/resources/BasicBirthday-1.0-SNAPSHOT.jar");
            command.add("org.example.BirthdayArgs");
            command.add(String.valueOf(numberOfCases));

            for (int i = 0; i < numberOfCases; i++) {
                int randomAge = random.nextInt(0, 124);
                ages.add(randomAge);
                command.add(Integer.toString(randomAge));
            }

            System.out.println(ages);
            String[] finalCommand = command.toArray(new String[0]);
            Process process = runtime.exec(finalCommand);

            Scanner scanner = new Scanner(process.getInputStream());
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
