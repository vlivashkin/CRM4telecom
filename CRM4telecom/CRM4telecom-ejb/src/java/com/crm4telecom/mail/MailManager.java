package com.crm4telecom.mail;

import com.crm4telecom.jpa.Order;
import com.crm4telecom.jpa.OrderProcessing;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class MailManager {

    private final Logger log = Logger.getLogger ( getClass ().getName () ) ;
    private final String from = "crm4telecom@gmail.com";
    private final String password = "crm4telecom2Q";

    private final Boolean auth = true;
    private final Boolean startTLS = true;
    private final String host = "smtp.gmail.com";
    private final Integer port = 587;

    public void statusChangedEmail(Order order, List<OrderProcessing> steps) throws MessagingException {
        String subject = "Order #" + order.getOrderId();

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty("directive.set.null.allowed", true);
        ve.init();
        Template t = ve.getTemplate("com/crm4telecom/mail/mailtemplate.vm");

        VelocityContext context = new VelocityContext();
        context.put("firstName", order.getCustomer().getFirstName());
        context.put("lastName", order.getCustomer().getLastName());
        context.put("orderId", order.getOrderId());
        context.put("status", order.getStatus());
        context.put("steps", steps);

        StringWriter writer = new StringWriter();
        t.merge(context, writer);

        send(order.getCustomer().getEmail(), subject, writer.toString());
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
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("illusionww@gmail.com"));
            message.setSubject(subject);
            message.setContent(text, "text/html");
            Transport.send(message);
             if (log.isInfoEnabled()) {
                log.info("Send message because changing order : " + subject + " to " + to);
            }
        } catch (MessagingException e) {
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("Can't send email message " + to+ " order : "+ subject);
            }
        }
    }
}
