package br.com.db1.meritmoney.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(name = "TRANSACOES")
@JsonIgnoreProperties
public class Transacao extends AbstractEntity<Long> {

    @ManyToOne
    private Pessoa remetente;

    @ManyToOne
    private Pessoa destinatario;

    //@NotBlank(message = "Informe um valor.")
    @Column(name = "valor", nullable = false, columnDefinition = "DECIMAL(5,2) DEFAULT 0.00")
    private BigDecimal valor;

    @Column(name = "mensagem", length = 80)
    private String mensagem;

    @Column(name = "data_operacao", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp dateTime = new Timestamp(new Date().getTime());

    public Pessoa getRemetente() {
        return remetente;
    }

    public void setRemetente(Pessoa remetente) {
        this.remetente = remetente;
    }

    public Pessoa getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Pessoa destinatario) {
        this.destinatario = destinatario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        final StringBuilder sb = new StringBuilder("Transacao: ");
        sb.append("\nCódigo da transação: ").append(getId());
        sb.append("\nRemetente: ").append(remetente.getNome());
        sb.append("\nDestinatário: ").append(destinatario.getNome());
        sb.append("\nValor: M$").append(numberFormat.format(valor).substring(numberFormat.format(valor).indexOf("$")+1));
        sb.append("\nMensagem: \n '").append(mensagem).append("'");
        sb.append("\nData e Hora: ").append(sdf.format(dateTime).replace(" ", " às "));
        return sb.toString();
    }
}
