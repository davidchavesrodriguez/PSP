package encryptbase64;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

public class EncryptBase64 {
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

            message.setSubject("Learning about transport");

            File file = new File("src/main/resources/transports.png");
            FileInputStream imageInFile = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            String imageBase64 = Base64.getEncoder().encodeToString(imageData);

            MimeBodyPart htmlPart = new MimeBodyPart();
            String htmlContent = "<h1>Hello everyone!</h1>\n" +
                    "Let's talk about when we use 'the' with transport." +
                    "<img src=\"data:image/png;base64," + imageBase64 + "\"/>" +
                    "<p>First, we often use the when we are talking about a form of transport as a general idea." +
                    "We usually do this with public transport (not with cars or bikes) and we usually " +
                    "useverbs such as take, be on, get on and get off:</p>" +
                    "<ul><li>We took the bus to school.</li>\n" +
                    "<li>Julie's on the train at the moment.</li>\n" +
                    "<li>She gets off the underground in central London.</li></ul>\n" +
                    "In all of these examples, I'm not talking about a particular bus, train or plane but rather the system of transport as an idea.\n" +
                    "However, we use 'no article' when we use a form of transport with by:\n" +
                    "\n" +
                    "<ul><li>We travelled by plane.</li>\n" +
                    "<li>He goes to work by bus.</li>\n" +
                    "<li>We went to Scotland by train.</li></ul>\n" +
                    "Remember, we can't say <del>'by foot'</del> or <del>'by feet'</del> when we're talking about walking. We say 'on foot' (also no article)\n" +
                    "\n" +
                    "I hope that helps, and really good luck with your English!\n" +
                    "\n" +
                    "\n" +
                    "Pam";
            htmlPart.setContent(htmlContent, "text/html");

            MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.attachFile("src/main/resources/transports.png");
            imagePart.setContentID("<transports>");
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
