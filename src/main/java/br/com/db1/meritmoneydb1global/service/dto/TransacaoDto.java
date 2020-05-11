package br.com.db1.meritmoneydb1global.service.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransacaoDto {

    private PessoaDto remetente;

    private PessoaDto destinatario;

    private BigDecimal valor;

    private String mensagem;

    private Timestamp dateTime;

    public TransacaoDto() {
    }

    public TransacaoDto(PessoaDto remetente, PessoaDto destinatario, BigDecimal valor, String mensagem, Timestamp dateTime) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.valor = valor;
        this.mensagem = mensagem;
        this.dateTime = dateTime;
    }

    public PessoaDto getRemetente() {
        return remetente;
    }

    public void setRemetente(PessoaDto remetente) {
        this.remetente = remetente;
    }

    public PessoaDto getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(PessoaDto destinatario) {
        this.destinatario = destinatario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
}
