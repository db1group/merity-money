package br.com.db1.meritmoney.email;

import br.com.db1.meritmoney.domain.Pessoa;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Service
public class ForgotPasswordEmailService {

    private final IEmailService emailService;

    public ForgotPasswordEmailService(IEmailService emailService) {
        this.emailService = emailService;
    }

    public void sendChangePasswordHTMLEmail(Pessoa pessoa, String hash) {
        try {
            String servidor = "http://localhost:8080/novasenha/";
            String encodingOptions = "text/html; charset=utf-8";

            String template = emailService.getTemplate("ForgotPasswordTemplate");
            String emailBody = template
                    .replaceAll("#link#", servidor + hash);
            MimeMultipart mimeMultipart = new MimeMultipart();

            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(emailBody, encodingOptions);

            mimeMultipart.addBodyPart(mbp);

            MimeMessage mimeMessage = emailService.getMimeMessage(pessoa.getEmail());
            mimeMessage.setContent(mimeMultipart);
            mimeMessage.setSubject("Solicitação de troca de email");

            emailService.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
