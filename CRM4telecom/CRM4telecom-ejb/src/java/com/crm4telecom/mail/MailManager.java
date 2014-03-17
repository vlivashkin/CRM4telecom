package com.crm4telecom.mail;

import com.crm4telecom.jpa.Order;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailManager {

    private final String from = "crm4telecom@gmail.com";
    private final String password = "crm4telecom2Q";

    private final Boolean auth = true;
    private final Boolean startTLS = true;
    private final String host = "smtp.gmail.com";
    private final Integer port = 587;

    public void statusChangedEmail(Order order) {
        String subject = "Order #" + order.getOrderId() + " status changed to " + order.getStatus().name();

        String text = "Dear " + order.getCustomer().getFirstName() + " "
                + order.getCustomer().getLastName() + ",<br><br><br>"
                + "The order #" + order.getOrderId() + " status changed to "
                + order.getStatus().name() + "<br><br> crm4telecom";

        send(order.getCustomer().getEmail(), subject, text);
    }

    public void send(String to, String subject, String text) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.auth", auth.toString());
        properties.setProperty("mail.smtp.starttls.enable", startTLS.toString());
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port.toString());

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("illusionww@gmail.com"));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setContent(text, "text/html");

            // Send message
            Transport.send(message);
            System.out.println("Sent message \"" + subject + "\" to " + to);
        } catch (MessagingException e) {
            System.err.println(e);
        }
    }
}
