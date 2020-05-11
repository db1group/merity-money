package br.com.db1.meritmoneydb1global.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "EQUIPES")
public class Equipe extends AbstractEntity<Long> {

    @NotBlank(message = "Informe um nome.")
    @Column(name = "nome", nullable = false, unique = true, length = 60)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "path_foto")
    private String pathFoto;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Equipe() {
    }
}
