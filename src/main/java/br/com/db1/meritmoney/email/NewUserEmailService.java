package br.com.db1.meritmoney.email;

import br.com.db1.meritmoney.domain.Pessoa;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Service
public class NewUserEmailService extends AbstractEmailService {

    //TODO jogar para um emial constants ou enum
    public static final String NEW_USER_TEMPLATE = "NewUserTemplate";

    public void emailSenderFromNewPessoa(Pessoa pessoa, String password) throws MessagingException {

        MimeMessage message = getMimeMessage(pessoa);
        String content = "Seu email: " + pessoa.getEmail() + "<br> Sua senha: " + password;
        message.setContent(content, "text/html; charset=utf-8");

        send(message);
    }

    public void emailHTMLSenderFromPessoa(Pessoa pessoa, String password) {
        try {

            String encodingOptions = "text/html; charset=utf-8";

            String template = getTemplate(NEW_USER_TEMPLATE);
            String emailBody = template
                    .replaceAll("#email#", pessoa.getEmail())
                    .replaceAll("#senha#", password);

            MimeMultipart mimeMultipart = new MimeMultipart();

            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(emailBody, encodingOptions);

            mimeMultipart.addBodyPart(mbp);

            MimeMessage mimeMessage = getMimeMessage(pessoa);
            mimeMessage.setContent(mimeMultipart);
            mimeMessage.setSubject("Você tem uma nova conta em MeritMoney");

            send(mimeMessage);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
