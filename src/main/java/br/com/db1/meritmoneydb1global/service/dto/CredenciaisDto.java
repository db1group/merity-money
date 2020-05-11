package br.com.db1.meritmoneydb1global.service.dto;

import br.com.db1.meritmoneydb1global.domain.AbstractEntity;

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
