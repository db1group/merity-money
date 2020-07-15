package br.com.db1.meritmoney.email;

import br.com.db1.meritmoney.domain.Pessoa;
import br.com.db1.meritmoney.domain.Transacao;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.*;
import java.text.SimpleDateFormat;

@Service
public class TransacaoEmailService {

    private final IEmailService emailService;

    public TransacaoEmailService(IEmailService emailService) {
        this.emailService = emailService;
    }

    public void emailSenderFromTransacao(Transacao transacao, Pessoa destinatario) throws MessagingException {

        MimeMessage message = emailService.getMimeMessage(destinatario.getEmail());
        message.setContent(transacao.toString(), "text/html; charset=utf-8");

        emailService.send(message);
    }

    public void emailHTMLSenderFromTransacao(Transacao transacao, Pessoa destinatario) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dateTime = sdf.format(transacao.getDateTime()).replace(" ", " às ");

            String encodingOptions = "text/html; charset=utf-8";

            String template = emailService.getTemplate("TransacaoEmail");
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

            MimeMessage mimeMessage = emailService.getMimeMessage(destinatario.getEmail());
            mimeMessage.setContent(mimeMultipart);
            mimeMessage.setSubject(String.format("Você %s M$%.2f.",auxiliarAssunto, transacao.getValor()));

            emailService.send(mimeMessage);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
