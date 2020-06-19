package br.com.db1.meritmoney.service;

import br.com.db1.meritmoney.domain.Pessoa;
import br.com.db1.meritmoney.domain.Transacao;
import br.com.db1.meritmoney.email.TransacaoEmailService;
import br.com.db1.meritmoney.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class TransacaoService {

    private TransacaoRepository transacaoRepository;

    private TransacaoEmailService transacaoEmailSender;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
        this.transacaoEmailSender = new TransacaoEmailService();
    }

    public Transacao salvarTransacao(Transacao transacao) throws MessagingException {
        transacaoEmailSender.emailHTMLSenderFromTransacao(transacao, transacao.getRemetente());
        transacaoEmailSender.emailHTMLSenderFromTransacao(transacao, transacao.getDestinatario());
        return transacaoRepository.save(transacao);
    }

    public List<Transacao> listarTransacoes() {
        return transacaoRepository.findAll();
    }

    public Transacao buscarPorId(Long id) {
        return transacaoRepository.getOne(id);
    }

    public Transacao deletarPessoa(Long id) {
        transacaoRepository.deleteById(id);
        return transacaoRepository.getOne(id);
    }

    public List<Transacao> buscarDestinatarioPorId(Long id) {
        return transacaoRepository.findAllByDestinatarioIdOrderByDateTimeDesc(id);
    }

    public List<Transacao> buscarRemetentePorId(Long id) {
        return transacaoRepository.findAllByRemetenteIdOrderByDateTimeDesc(id);
    }

    public Integer contarPorRemetente(Pessoa pessoa) {
        return transacaoRepository.countByRemetente(pessoa);
    }

    public Integer contarPorDestinatario(Pessoa pessoa) {
        return transacaoRepository.countByDestinatario(pessoa);
    }

    public Timestamp buscarUltimoEnvio(Pessoa pessoa) {
        return transacaoRepository.findLastByRemetente(pessoa);
    }

    public Timestamp buscarUltimoRecebido(Pessoa pessoa) {
        return transacaoRepository.findLastByDestinatario(pessoa);
    }

}
