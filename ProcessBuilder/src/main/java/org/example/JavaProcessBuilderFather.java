package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class JavaProcessBuilderFather {
    public static void main(String[] args) {

        // Ask for input
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many numbers are you telling me?");
        int numberOfInputs = scanner.nextInt();

        ArrayList<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 0; i < numberOfInputs; i++) {
            switch (i) {
                case 0:
                    System.out.println("Tell me the 1st number: ");
                    break;
                case 1:
                    System.out.println("Tell me the 2nd number: ");
                    break;
                case 2:
                    System.out.println("Tell me the 3rd number: ");
                    break;
                default:
                    int iteration = i + 1;
                    String suffix;
                    if (iteration % 10 == 1 && iteration % 100 != 11) {
                        suffix = "st";
                    } else if (iteration % 10 == 2 && iteration % 100 != 12) {
                        suffix = "nd";
                    } else if (iteration % 10 == 3 && iteration % 100 != 13) {
                        suffix = "rd";
                    } else {
                        suffix = "th";
                    }
                    System.out.println("Tell me the " + iteration + suffix + " number: ");
                    break;
            }
            listOfNumbers.add(scanner.nextInt());
        }

        // Execute the method
        String resultFromFirst = runProcess("org.example.JavaProcessBuilder", numberOfInputs, listOfNumbers);
        System.out.println("Output from JavaProcessBuilder: " + resultFromFirst);

        String resultFromSecond = runProcess("org.example.JavaProcessBuilder2", numberOfInputs, listOfNumbers);
        System.out.println("Output from JavaProcessBuilder2: " + resultFromSecond);
    }

    // Launch a process
    private static String runProcess(String className, int numberOfInputs, ArrayList<Integer> listOfNumbers) {
        Process process = null;
        String resultLine = "";
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", "target/classes", className);
            process = pb.start();

            // Send number of inputs and numbers to child
            OutputStream outputStream = process.getOutputStream();
            outputStream.write((numberOfInputs + "\n").getBytes());

            for (int number : listOfNumbers) {
                outputStream.write((number + "\n").getBytes());
            }

            outputStream.flush();
            outputStream.close();

            // Get child process input
            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                resultLine = line; //
            }
            bufferedReader.close();

        } catch (IOException e) {
            System.err.println("Error executing process: " + e.getMessage());
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return resultLine;
    }
}
