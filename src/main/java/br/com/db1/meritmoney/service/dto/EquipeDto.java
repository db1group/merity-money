package br.com.db1.meritmoney.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EquipeDto {

    @JsonProperty("id")
    private Long id;

    private String nome;

    private String descricao;

    private Integer numeroDeColaboradores;

    private String pathFoto;

    public EquipeDto() {
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
        this.nome = nome.toUpperCase();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    public Integer getNumeroDeColaboradores() {
        return numeroDeColaboradores;
    }

    public void setNumeroDeColaboradores(Integer numeroDeColaboradores) {
        this.numeroDeColaboradores = numeroDeColaboradores;
    }
}
