package attachment;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AttachmentMain {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/attachment.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String email = properties.getProperty("mail.smtp.user");
        String password = properties.getProperty("mail.smtp.password");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));

            // Añadir varios destinatarios (Yopmail, Gmail, Outlook)
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("A22DavidCR@yopmail.com,A22DavidCR@gmail.com,A22DavidCR@outlook.com")
            );

            message.setSubject("Correo con Adjuntos y HTML usando TLS");

            // Crear el cuerpo del mensaje con HTML
            MimeBodyPart bodyPartHTML = new MimeBodyPart();
            bodyPartHTML.setContent("<h1>¡Hola!</h1><p>Este es un correo con adjuntos y contenido HTML.</p>", "text/html");

            // Adjuntar archivo
            MimeBodyPart attachmentPart = new MimeBodyPart();
            File file = new File("src/main/resources/pdf.pdf");
            attachmentPart.attachFile(file);

            // Combinar el contenido y el adjunto
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPartHTML);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // Enviar el correo
            Transport.send(message);
            System.out.println("Correo enviado con éxito.");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
