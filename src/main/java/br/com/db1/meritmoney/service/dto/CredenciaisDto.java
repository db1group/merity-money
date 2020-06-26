package br.com.db1.meritmoney.service.dto;

import br.com.db1.meritmoney.domain.AbstractEntity;

import java.io.Serializable;

public class CredenciaisDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String senha;

    public CredenciaisDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
