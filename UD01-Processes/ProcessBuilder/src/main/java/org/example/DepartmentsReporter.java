package org.example;

import java.io.*;

public class DepartmentsReporter {
    public static void main(String[] args) throws IOException {
        String departmentsPath = "src/main/resources/departments";
        String reportFile = "src/main/resources/departmentReport.txt";

        File mainFolder = new File(departmentsPath);
        File[] fileList = mainFolder.listFiles();

        // Writer started for report
        try (FileWriter reportWriter = new FileWriter(reportFile)) {
            // Running a command for each file
            for (File file : fileList) {
                ProcessBuilder pb = new ProcessBuilder(
                        "java", "-cp", "target/classes", "org.example.DepartmentChecker", file.getPath());

                try {
                    // Start child process
                    Process process = pb.start();

                    // Read each line of the child process
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    // Write each line to the report
                    while ((line = bufferedReader.readLine()) != null) {
                        reportWriter.write(line + "\n");
                    }

                    // Make sure the process finished
                    int exitCode = process.waitFor();

                    if (exitCode == 0) {
                        System.out.println("Process for " + file.getName() + " done");
                    } else {
                        System.out.println("Process for " + file.getName() + " failed");
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }


    }
}
