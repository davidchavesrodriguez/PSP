package org.example;

import java.io.*;

public class DepartmentChecker {
    public static void main(String[] args) throws FileNotFoundException {
        String pathToFile = args[0];

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            double counter = 0;
            while ((line = bufferedReader.readLine()) != null) {
                counter += Double.parseDouble(line);
            }
            System.out.println(args[0]);
            System.out.println(counter);
            System.out.println("------------------------");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
