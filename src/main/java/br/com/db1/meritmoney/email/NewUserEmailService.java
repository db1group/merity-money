package br.com.db1.meritmoney.email;

import br.com.db1.meritmoney.domain.Pessoa;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Service
public class NewUserEmailService {

    private final IEmailService emailService;

    //TODO jogar para um emial constants ou enum
    public static final String NEW_USER_TEMPLATE = "NewUserTemplate";

    public NewUserEmailService(IEmailService emailService) {
        this.emailService = emailService;
    }

    public void emailSenderFromNewPessoa(Pessoa pessoa, String password) throws MessagingException {

        MimeMessage message = emailService.getMimeMessage(pessoa.getEmail());
        String content = "Seu email: " + pessoa.getEmail() + "<br> Sua senha: " + password;
        message.setContent(content, "text/html; charset=utf-8");

        emailService.send(message);
    }

    public void emailHTMLSenderFromPessoa(Pessoa pessoa, String password) {
        try {

            String encodingOptions = "text/html; charset=utf-8";

            String template = emailService.getTemplate(NEW_USER_TEMPLATE);
            String emailBody = template
                    .replaceAll("#email#", pessoa.getEmail())
                    .replaceAll("#senha#", password);

            MimeMultipart mimeMultipart = new MimeMultipart();

            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(emailBody, encodingOptions);

            mimeMultipart.addBodyPart(mbp);

            MimeMessage mimeMessage = emailService.getMimeMessage(pessoa.getEmail());
            mimeMessage.setContent(mimeMultipart);
            mimeMessage.setSubject("Você tem uma nova conta em MeritMoney");

            emailService.send(mimeMessage);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
