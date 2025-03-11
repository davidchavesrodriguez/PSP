package gal.dchaves.p4_05;

import jakarta.mail.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Pop3 {
    public static void main(String[] args) throws FileNotFoundException {
        try (InputStream input = new FileInputStream("src/main/resources/pop3.properties")) {
            Properties properties = new Properties();
            properties.load(input);

//            Authenticator authenticator = new Authenticator() {
//                @Override
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication("A22DavidCR@gmail.com", "xixj vyhk wxlg guvg");
//                }
//            };

            Session session = Session.getDefaultInstance(properties);

            Store store = session.getStore("pop3");
            store.connect("A22DavidCR@gmail.com", "xixj vyhk wxlg guvg");

            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            Message[] messages = folder.getMessages();
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("Subject: " + message.getSubject());
                System.out.println("Text: " + message.getContent().toString());
            }

        } catch (IOException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
