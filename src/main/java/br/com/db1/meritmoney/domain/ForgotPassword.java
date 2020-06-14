package br.com.db1.meritmoney.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "ForgorPassword")
public class ForgotPassword extends AbstractEntity<Long> {

    @ManyToOne
    private Pessoa pessoa;

    @NotNull
    @Column(name = "hash", nullable = false, unique = true, length = 70)
    private String hash;

    @Column(name = "data_geracao", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp dateTime;

    @Column(name = "used", nullable = false)
    private boolean isUsed;

    public ForgotPassword(@NotNull Pessoa pessoa, @NotNull String hash) {
        this.pessoa = pessoa;
        this.hash = hash;
        this.dateTime = new Timestamp(new Date().getTime());
        this.isUsed = false;
    }

    public ForgotPassword() {
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
