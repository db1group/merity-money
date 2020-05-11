package br.com.db1.meritmoneydb1global.domain;

import br.com.db1.meritmoneydb1global.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "PESSOAS")
public class Pessoa extends AbstractEntity<Long> {

    @NotBlank(message = "Informe um nome.")
    @Size(min = 5, max = 40, message = "O nome deve conter entre {min} e {max} caracteres.")
    @Column(name = "nome", nullable = false, unique = true, length = 70)
    private String nome;

    @NotBlank(message = "Informe um email.")
    @Column(name = "email", nullable = false, unique = true, length = 70)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    @Column(name = "path_foto")
    private String pathFoto;

    @ManyToOne
    @JsonBackReference("equipe_pessoa")
    private Equipe equipe;

    public Pessoa() {
        addPerfil(Perfil.COLABORADOR);
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
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

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream()
                .map(perfil -> {
                    try {
                        return Perfil.toEnum(perfil);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCod());
    }


    public void setPerfis(Set<Integer> perfis) {
        this.perfis = perfis;
    }
}
