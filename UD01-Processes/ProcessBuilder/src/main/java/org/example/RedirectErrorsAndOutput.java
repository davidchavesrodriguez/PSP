package org.example;

import java.io.*;

public class RedirectErrorsAndOutput {
    public static void main(String[] args) throws IOException {
        String errorsFile = "src/main/resources/errors.txt";
        String outputFile = "src/main/resources/output.txt";


        ProcessBuilder pb = new ProcessBuilder(
                "java", "-cp", "target/classes", "org.example.AddEvenNumbersBetweenTwo", "1", "6");
        Process process= pb.start();

        try (BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(process.getInputStream()))){
            String line;
            while ((line = bufferedReader.readLine())!=null) {
                System.out.println(line);
            }

            try (BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))){
                BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
                bufferedWriter.write(userInputReader);
            }
        }

    }
}
