package com.crm4telecom.mail;

import com.crm4telecom.jpa.Order;
import com.crm4telecom.jpa.OrderProcessing;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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

    private final static int poolSize = 10;
    private final static ScheduledExecutorService service = Executors.newScheduledThreadPool(poolSize);
    private final static Logger log = Logger.getLogger(MailManager.class);
    private final static String from = "crm4telecom@gmail.com";
    private final static String password = "crm4telecom2Q";
    private final static Boolean auth = true;
    private final static Boolean startTLS = true;
    private final static String host = "smtp.gmail.com";
    private final static Integer port = 587;
    private final static int maxAttemp = 3;

    public void statusChangedEmail(final Order order, List<OrderProcessing> steps) throws MessagingException {
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
        context.put("step", order.getProcessStep().getLabel());
        context.put("steps", steps);
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        service.schedule(new SendChecker(
                service.schedule(new EmailSender(0, order.getCustomer().getEmail(), "Order #" + order.getOrderId(), writer.toString()), 0, TimeUnit.MILLISECONDS), order.getCustomer().getEmail(), "Order #" + order.getOrderId(), writer.toString()),
                100, TimeUnit.MILLISECONDS);
    }

    public static boolean send(String to, final String subject, final String text) throws Exception {

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

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(to));
        message.setSubject(subject);
        message.setContent(text, "text/html");
        Transport.send(message);
        //   throw new MessagingException();
        return true;
    }

    private static class EmailSender implements Callable {

        private final int attemp;
        private final String email;
        private final String subject;
        private final String text;

        public EmailSender(int attemp, String email, String subject, String text) {
            this.attemp = attemp;
            this.email = email;
            this.subject = subject;
            this.text = text;
        }

        @Override
        public ScheduledFuture call() throws MessagingException, Exception {
            boolean sent = send(this.email, this.subject, this.text);

            if (sent) {
                return null;
            }
            if (attemp >= maxAttemp) {
                throw new RuntimeException("Amount of attemps more than maxAttemp");
            }
            if (log.isEnabledFor(Priority.WARN)) {
                log.warn("Can't send email attemp : " + (attemp + 1));
            }
            return service.schedule(new SendChecker(
                    service.schedule(new EmailSender(this.attemp + 1, this.email, this.subject, this.text), 100, TimeUnit.MILLISECONDS), this.email, this.subject, this.text),
                    3000, TimeUnit.MILLISECONDS);
        }
    }

    private static class SendChecker implements Runnable {

        private final Future<Boolean> itemToCheck;
        private final String email;
        private final String subject;
        private final String text;

        public SendChecker(Future<Boolean> itemToCheck, String email, String subject, String text) {
            this.itemToCheck = itemToCheck;
            this.email = email;
            this.subject = subject;
            this.text = text;
        }

        @Override
        public void run() {
            try {
                if (itemToCheck.get(1000, TimeUnit.MILLISECONDS) == null) {
                    if (log.isInfoEnabled()) {
                        log.info("Send message because changing order : " + this.subject + " to " + this.email);
                    }
                }
            } catch (InterruptedException ex) {
                if (log.isEnabledFor(Priority.ERROR)) {
                    log.error("Can't check sending email because process was interrupted", ex);
                }
            } catch (ExecutionException ex) {
                if (log.isEnabledFor(Priority.ERROR)) {
                    log.error("Cant send email.", ex);
                }
            } catch (TimeoutException ex) {
                service.schedule(new SendChecker(this.itemToCheck, this.email, this.subject, this.text), 300, TimeUnit.MILLISECONDS);
            }
        }

    }
}
