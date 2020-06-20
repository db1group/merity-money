package br.com.db1.meritmoney.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class PessoaDto {

    private Long id;
    private String nome;
    private String email;
    private String pathFoto;
    private String linkedin;
    private EquipeDto equipe;
    private BigDecimal saldo;
    private BigDecimal credito;
    private BigDecimal debito;


    public PessoaDto(
            @JsonProperty("id") Long id,
            @JsonProperty("nome") String nome,
            @JsonProperty("email") String email,
            @JsonProperty("linkedin") String linkedin,
            @JsonProperty("pathFoto") String pathFoto,
            @JsonProperty("equipe") EquipeDto equipe
    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.linkedin = linkedin;
        this.pathFoto = pathFoto;
        this.equipe = equipe;
    }

    public PessoaDto(Long id) {
        this.id = id;
    }

    public PessoaDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    public EquipeDto getEquipe() {
        return equipe;
    }

    public void setEquipe(EquipeDto equipe) {
        this.equipe = equipe;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getCredito() {
        return credito;
    }

    public void setCredito(BigDecimal credito) {
        this.credito = credito;
    }

    public BigDecimal getDebito() {
        return debito;
    }

    public void setDebito(BigDecimal debito) {
        this.debito = debito;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }
}
