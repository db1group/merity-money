package br.com.db1.meritmoneydb1global.service.dto;

public class TrocaSenhaDto {

    private String hash;
    private String newPassword;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
