package gal.dchaves.raffle;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.*;
import java.util.Properties;

public class EmailService {
    private final String smtpHost;
    private final String smtpPort;
    private final String username;
    private final String password;

    public EmailService(String smtpHost, String smtpPort, String username, String password) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
    }

    public void sendEmail(String to, String subject, String body) throws FileNotFoundException {
        try (InputStream input = new FileInputStream("src/main/resources/gmail.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
                System.out.println("Mail sent to " + username);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
