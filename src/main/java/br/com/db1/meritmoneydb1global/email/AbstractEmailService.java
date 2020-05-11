package br.com.db1.meritmoneydb1global.email;

import br.com.db1.meritmoneydb1global.domain.Pessoa;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class AbstractEmailService implements EmailService {

    protected Session getSession(String email, String senha) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        return Session.getDefaultInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, senha);
                    }
                });
    }

    @Override
    public void send(MimeMessage msg) throws MessagingException {
        Transport.send(msg);
    }

    protected String getTemplate(String path) {
        path = "C:/DB1/projetos/merit-money-back/merit-money/src/main/resources/email/"
                + path
                + ".html";
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
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
        String emailRemetente = "gabrielcilico@gmail.com";
        MimeMessage message = new MimeMessage(getSession(emailRemetente, "rbmvkqwjrfljawqo"));
        message.setFrom(new InternetAddress(emailRemetente));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(pessoa.getEmail()));
        return message;
    }
}
