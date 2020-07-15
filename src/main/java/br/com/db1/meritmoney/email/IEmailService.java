package br.com.db1.meritmoney.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface IEmailService {

    void send(MimeMessage msg) throws MessagingException;
    String getTemplate(String templateName);
    MimeMessage getMimeMessage(String email) throws MessagingException;
}
