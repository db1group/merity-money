package br.com.db1.meritmoneydb1global.email;

import br.com.db1.meritmoneydb1global.domain.Pessoa;
import br.com.db1.meritmoneydb1global.domain.Transacao;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.*;
import java.text.SimpleDateFormat;

@Service
public class TransacaoEmailService extends AbstractEmailService {

    public void emailSenderFromTransacao(Transacao transacao, Pessoa destinatario) throws MessagingException {

        MimeMessage message = getMimeMessage(destinatario);
        message.setContent(transacao.toString(), "text/html; charset=utf-8");

        send(message);
    }

    public void emailHTMLSenderFromTransacao(Transacao transacao, Pessoa destinatario) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dateTime = sdf.format(transacao.getDateTime()).replace(" ", " às ");

            String encodingOptions = "text/html; charset=utf-8";

            String template = getTemplate("TransacaoEmail");
            String auxiliarAssunto = (transacao.getRemetente().equals(destinatario)) ? "enviou" : "recebeu";
            String emailBody = template
                    .replaceAll("#attemp#", auxiliarAssunto)
                    .replaceAll("#remetente#", transacao.getRemetente().getNome())
                    .replaceAll("#destinatario#", transacao.getDestinatario().getNome())
                    .replaceAll("#valor#", transacao.getValor().toString())
                    .replaceAll("#data_hora#", dateTime)
                    .replaceAll("#mensagem#", transacao.getMensagem());

            MimeMultipart mimeMultipart = new MimeMultipart();

            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(emailBody, encodingOptions);

            mimeMultipart.addBodyPart(mbp);

            MimeMessage mimeMessage = getMimeMessage(destinatario);
            mimeMessage.setContent(mimeMultipart);
            mimeMessage.setSubject(String.format("Você %s M$%.2f.",auxiliarAssunto, transacao.getValor()));

            send(mimeMessage);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
