package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        try (InputStream input= new FileInputStream("src/main/resources/smtp.properties")){
            Properties mailProperties= new Properties();
            mailProperties.load(input);
            System.out.println(mailProperties.getProperty("mail.smtp.host"));
        }

    }
}
