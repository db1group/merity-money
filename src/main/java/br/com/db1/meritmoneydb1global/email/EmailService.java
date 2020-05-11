package br.com.db1.meritmoneydb1global.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface EmailService {

    void send(MimeMessage msg) throws MessagingException;
}
