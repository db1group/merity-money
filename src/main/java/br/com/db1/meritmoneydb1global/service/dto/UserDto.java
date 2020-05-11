package br.com.db1.meritmoneydb1global.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String senha;

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
