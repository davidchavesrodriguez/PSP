package attachimage;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AttachImageMain {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/attachment.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String user = properties.getProperty("mail.smtp.user");
        String password = properties.getProperty("mail.smtp.password");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("A22DavidCR@yopmail.com,A22DavidCR@gmail.com,A22DavidCR@outlook.com"));

            message.setSubject("Correo con imagen incrustada usando Content-ID");

            MimeBodyPart htmlPart = new MimeBodyPart();
            String htmlContent = "<h1>Hola, este es un correo con una imagen incrustada:</h1>" +
                    "<img src='cid:professionals' style='width: 200px; height: 200px;'>";
            htmlPart.setContent(htmlContent, "text/html");

            MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.attachFile("src/main/resources/professionals.png");
            imagePart.setContentID("<professionals>");
            imagePart.setDisposition(MimeBodyPart.INLINE);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Correo enviado con imagen incrustada.");

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
