package com.crm4telecom.mail;

import com.crm4telecom.jpa.Order;
import com.crm4telecom.jpa.OrderProcessing;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
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

    private ScheduledExecutorService service = null;
    private final Logger log = Logger.getLogger(getClass().getName());
    private final String from = "crm4telecom@gmail.com";
    private final String password = "crm4telecom2Q";
    private FutureArray arrf = null;
    private final Boolean auth = true;
    private final Boolean startTLS = true;
    private final String host = "smtp.gmail.com";
    private final Integer port = 587;

    public void statusChangedEmail(final Order order, List<OrderProcessing> steps) throws MessagingException {
        final String subject = "Order #" + order.getOrderId();

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
        final StringWriter writer = new StringWriter();
        t.merge(context, writer);
        if (service == null) {
            service = Executors.newScheduledThreadPool(6);
        }
        if (arrf == null) {

            arrf = new FutureArray();
            arrf.arrfuture[0] = service.scheduleAtFixedRate(new Runnable() {

                @Override
                public void run() {
                    //    while (true) {
                    int i = 1;
                    while (i < arrf.arrfuture.length) {
                        if (arrf.arrfuture[i] != null) {
                            try {
                                boolean flag = (boolean) arrf.arrfuture[i].get(5, TimeUnit.SECONDS);
                                if (flag == true) {
                                    if (log.isInfoEnabled()) {
                                        log.info("Send message because changing order : " + arrf.subject[i] + " to " + arrf.email[i]);
                                    }
                                    arrf.arrfuture[i] = null;
                                }

                            } catch (Exception ex) {
                                if (arrf.attempt[i] != 3) {
                                    if (log.isEnabledFor(Priority.WARN)) {
                                        log.warn("Can't send email attempt :" + (arrf.attempt[i] + 1) + ex.toString());
                                    }
                                    final int j = i;
                                    arrf.arrfuture[i] = service.submit(new Callable<Boolean>() {
                                        @Override
                                        public Boolean call() throws Exception {
                                            MailManager m = new MailManager();
                                            m.send(arrf.email[j], arrf.subject[j], arrf.text[j]);
                                            return true;
                                        }
                                    }
                                    );

                                    arrf.attempt[i]++;
                                } else {
                                    if (log.isEnabledFor(Priority.ERROR)) {
                                        log.error("Can't send email. Address : " + arrf.email[i] + " order" + arrf.subject[i]);
                                    }
                                    arrf.arrfuture[i] = null;
                                }
                            }
                        }
                        i++;
                    }
                }
            }, 0, 2, TimeUnit.SECONDS
            );
        }
        Future<Boolean> f = service.schedule(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                MailManager m = new MailManager();
                m.send(order.getCustomer().getEmail(), subject, writer.toString());
                return true;
            }
        }, 100, TimeUnit.MILLISECONDS
        );
        int i = 1;
        boolean flag = true;
        while (i < arrf.arrfuture.length && flag) {
            if (arrf.arrfuture[i] == null) {
                arrf.arrfuture[i] = f;
                arrf.email[i] = order.getCustomer().getEmail();
                arrf.subject[i] = subject;
                arrf.text[i] = writer.toString();
                arrf.attempt[i] = 0;
                flag = false;
            }
            i++;
        }
    }

    public void send(String to, String subject, String text) throws MessagingException {
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
                new InternetAddress("illusionww@gmail.com"));
        message.setSubject(subject);
        message.setContent(text, "text/html");
        Transport.send(message);

     //   throw new MessagingException();
    }

    private static class FutureArray {

        public Future[] arrfuture = new Future[6];
        public String[] email = new String[6];
        public String[] text = new String[6];
        public String[] subject = new String[6];
        public int[] attempt = new int[6];
    }
}
