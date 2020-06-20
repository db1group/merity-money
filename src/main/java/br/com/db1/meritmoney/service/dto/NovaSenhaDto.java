package br.com.db1.meritmoney.service.dto;

public class NovaSenhaDto {

    private String senhaAntiga;
    private String novaSenha;

    public NovaSenhaDto(String senhaAntiga, String novaSenha) {
        this.senhaAntiga = senhaAntiga;
        this.novaSenha = novaSenha;
    }

    public NovaSenhaDto() {
    }

    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    public void setSenhaAntiga(String senhaAntiga) {
        this.senhaAntiga = senhaAntiga;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
