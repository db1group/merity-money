package br.com.db1.meritmoneydb1global.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public final class TransacaoBuilder {
    private Pessoa remetente;
    private Pessoa destinatario;
    private Long id;
    private BigDecimal valor;
    private String mensagem;
    private Timestamp dateTime = new Timestamp(new Date().getTime());

    private TransacaoBuilder() {
    }

    public static TransacaoBuilder aTransacao() {
        return new TransacaoBuilder();
    }

    public TransacaoBuilder withRemetente(Pessoa remetente) {
        this.remetente = remetente;
        return this;
    }

    public TransacaoBuilder withDestinatario(Pessoa destinatario) {
        this.destinatario = destinatario;
        return this;
    }

    public TransacaoBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public TransacaoBuilder withValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public TransacaoBuilder withMensagem(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }

    public TransacaoBuilder withDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public Transacao build() {
        Transacao transacao = new Transacao();
        transacao.setRemetente(remetente);
        transacao.setDestinatario(destinatario);
        transacao.setId(id);
        transacao.setValor(valor);
        transacao.setMensagem(mensagem);
        transacao.setDateTime(dateTime);
        return transacao;
    }
}
