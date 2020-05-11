package br.com.db1.meritmoneydb1global.email;

import br.com.db1.meritmoneydb1global.domain.Pessoa;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Service
public class ForgotPasswordEmailService extends AbstractEmailService {

    public void sendChangePasswordHTMLEmail(Pessoa pessoa, String hash) {
        try {
            String servidor = "http://localhost:8080/novasenha/";
            String encodingOptions = "text/html; charset=utf-8";

            String template = getTemplate("ForgotPasswordTemplate");
            String emailBody = template
                    .replaceAll("#link#", servidor + hash);
            MimeMultipart mimeMultipart = new MimeMultipart();

            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(emailBody, encodingOptions);

            mimeMultipart.addBodyPart(mbp);

            MimeMessage mimeMessage = getMimeMessage(pessoa);
            mimeMessage.setContent(mimeMultipart);
            mimeMessage.setSubject("Solicitação de troca de email");

            send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
