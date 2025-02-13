package minirelay;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MinirelayMain {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/smtp.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Habilitar la depuración SMTP
        properties.put("mail.debug", "true");

        Session session = Session.getInstance(properties);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.user")));
            message.setRecipient(Message.RecipientType.TO, new
                    InternetAddress(properties.getProperty("mail.smtp.user"), properties.getProperty("mail.smtp.user")));
            message.setSubject("Test Email with no authentication");
            message.setText("Hello! This is a test email sent using a mini relay server.");

            // Enviar el correo
            jakarta.mail.Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
