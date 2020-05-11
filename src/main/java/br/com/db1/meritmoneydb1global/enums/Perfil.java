package br.com.db1.meritmoneydb1global.enums;

import javax.persistence.Entity;
import javax.persistence.Table;


public enum Perfil {

    ADMIN(1, "ROLE_ADMIN"),
    MOD(2, "ROLE_MOD"),
    COLABORADOR(3, "ROLE_COLABORADOR");

    private Integer cod;
    private String descricao;

    Perfil(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static Perfil toEnum(Integer cod) throws IllegalAccessException {
        if (cod == null) {
            return null;
        }

        for (Perfil perfil : Perfil.values()) {
            if (cod.equals(perfil.getCod())) {
                return perfil;
            }
        }

        throw new IllegalAccessException("ID Inválido: " + cod);
    }
}
