package br.com.db1.meritmoney.email;

import br.com.db1.meritmoney.domain.Pessoa;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public abstract class AbstractEmailService implements EmailService {
    @Value("${email.password}")
    private String secret;

    @Value("${email.sender}")
    private String sender;

    protected Session getSession() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
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

    protected String getTemplate(final String emailTemplate) {
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

    protected MimeMessage getMimeMessage(Pessoa pessoa) throws MessagingException {
        MimeMessage message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress(sender));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(pessoa.getEmail()));
        return message;
    }
}
