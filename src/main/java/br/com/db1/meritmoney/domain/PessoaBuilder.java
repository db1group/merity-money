package br.com.db1.meritmoney.domain;

public final class PessoaBuilder {

    private String nome;
    private String email;
    private Long id;
    private String pathFoto;
    private String senha;
    private Equipe equipe;

    private PessoaBuilder() {
    }

    public static PessoaBuilder aPessoa() {
        return new PessoaBuilder();
    }

    public PessoaBuilder withNome(String nome) {
        this.nome = nome;
        return this;
    }

    public PessoaBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public PessoaBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PessoaBuilder withPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
        return this;
    }


    public PessoaBuilder withEquipe(Equipe equipe) {
        this.equipe = equipe;
        return this;
    }

    public Pessoa build() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setEmail(email);
        pessoa.setId(id);
        pessoa.setPathFoto(pathFoto);
        pessoa.setEquipe(equipe);
        return pessoa;
    }
}
