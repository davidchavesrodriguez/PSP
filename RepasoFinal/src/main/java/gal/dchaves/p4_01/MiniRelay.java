package gal.dchaves.p4_01;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MiniRelay {
    public static void main(String[] args) throws FileNotFoundException {

        String from = "A22DAvidCR@yopmail.com";
        String to = "A22DavidCR@yopmail.com";

        try (InputStream inputStream = new FileInputStream("src/main/resources/minirelay.properties")) {

            Properties properties = new Properties();

            properties.load(inputStream);

            Session session = Session.getDefaultInstance(properties);

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from, "David"));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(to, "Chaves"));
                message.setSubject("Holi");
                message.setText("Wue tal?");
                Transport.send(message);
                System.out.println("Sent");


            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
