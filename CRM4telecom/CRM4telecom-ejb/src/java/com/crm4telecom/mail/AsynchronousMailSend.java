package com.crm4telecom.mail;

import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.ejb.Singleton;
import org.apache.log4j.Priority;

@Singleton
public class AsynchronousMailSend   extends TimerTask
{

    private ExecutorService service = null;
    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(getClass().getName());
    private String email;
    private String subject;
    private String text;

    public AsynchronousMailSend() {
        if (service == null) {
            service = Executors.newFixedThreadPool(5);
        }
    }
    
    public  void fill(String email, String subject, String text) {
        this.email = email;
        this.subject = subject;
        this.text = text;
      //  this.send(email, subject, text);
    }

    public void send(final String email, final String subject, final String text) {
        Future<Boolean> f = service.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                MailManager m = new MailManager();
                m.send(email, subject, text);
                return true;
            }
        }
        );
        try {
            f.get();
        } catch (Exception ex) {
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("Send message because changing order : " + subject + " to " + email);
            }
        }

    }

    @Override
    public void run() {
        this.send(email,subject,text);
    }

}
