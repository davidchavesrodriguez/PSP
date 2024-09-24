package org.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class CmdDate2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        String[] command = {"cmd", "/C", "date"};
        Process process = runtime.exec(command);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        bufferedWriter.write("18-10-24");
        bufferedWriter.close();
        Scanner scannerIS = new Scanner(process.getInputStream());
        while (scannerIS.hasNextLine()) {
            System.out.println(scannerIS.nextLine());
        }
        scannerIS.close();
        int exitStatus = process.waitFor();
        System.out.println("Exit status process data: " + exitStatus);

        // tell what the error is
        if (exitStatus != 0) {
            Scanner scannerError = new Scanner(process.getErrorStream());
            while (scannerError.hasNextLine()) {
                System.out.println(scannerError.nextLine());
            }
            scannerError.close();
        }
    }
}