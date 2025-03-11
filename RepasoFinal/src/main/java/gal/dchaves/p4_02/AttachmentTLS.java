package gal.dchaves.p4_02;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.*;
import java.util.Properties;

public class AttachmentTLS {
    public static void main(String[] args) {
        try (InputStream input = new FileInputStream("src/main/resources/gmail.properties")) {
            Properties properties = new Properties();

            properties.load(input);

            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("A22DavidCR@gmail.com", "xixj vyhk wxlg guvg");
                }
            };

            Session session = Session.getDefaultInstance(properties, authenticator);

            MimeBodyPart bodyPart = new MimeBodyPart();
            String htmlMessage = "Html ola que tal";

            Message message = new MimeMessage(session);
            bodyPart.setContent(htmlMessage, "text/html");

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File("src/main/resources/chimchar.png"));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setFrom(new InternetAddress("A22DavidCR@gmail.com", "David"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("A22DavidCR@gmail.com", "Chaves"));
            message.setContent(multipart);
            Transport.send(message);

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
