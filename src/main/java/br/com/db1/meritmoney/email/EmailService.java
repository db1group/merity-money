package br.com.db1.meritmoney.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

@Service
public class EmailService implements IEmailService {

    private final String secret;
    private final String sender;
    private final String host;
    private final int port;
    private final boolean auth;

    protected EmailService(@Value("${application.mail.password}") String secret,
                           @Value("${application.mail.sender}") String sender,
                           @Value("${application.mail.smtp.host}") String host,
                           @Value("${application.mail.smtp.port}") int port,
                           @Value("${application.mail.smtp.auth}") boolean auth) {
        this.secret = secret;
        this.sender = sender;
        this.host = host;
        this.port = port;
        this.auth = auth;
    }

    protected Session getSession() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.auth", auth);
        prop.put("mail.smtp.socketFactory.port", port);
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        return Session.getDefaultInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sender, secret);
                    }
                });
    }

    @Override
    public void send(MimeMessage msg) throws MessagingException {
        Transport.send(msg);
    }

    @Override
    public String getTemplate(final String emailTemplate) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        String relativeEmailPath = "email/";
        String absEmailPath = classLoader.getResource(relativeEmailPath).getPath();
        String absTemplateEmailPath = absEmailPath+emailTemplate+".html";
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(absTemplateEmailPath));
            String line;
            while ((line = in.readLine()) != null) {
                contentBuilder.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    @Override
    public MimeMessage getMimeMessage(String email) throws MessagingException {
        MimeMessage message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress(sender));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        return message;
    }
}
