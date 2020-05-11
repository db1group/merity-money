package br.com.db1.meritmoneydb1global.domain;

import java.util.List;

public final class EquipeBuilder {
    private Long id;
    private String nome;
    private String pathFoto;
    private String descricao;

    private EquipeBuilder() {
    }

    public static EquipeBuilder anEquipe() {
        return new EquipeBuilder();
    }

    public EquipeBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public EquipeBuilder withNome(String nome) {
        this.nome = nome;
        return this;
    }

    public EquipeBuilder withPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
        return this;
    }

    public EquipeBuilder withDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Equipe build() {
        Equipe equipe = new Equipe();
        equipe.setId(id);
        equipe.setNome(nome);
        equipe.setPathFoto(pathFoto);
        equipe.setDescricao(descricao);
        return equipe;
    }
}
