package br.com.db1.meritmoneydb1global.service.dto;

import java.sql.Timestamp;

public class TransacaoInfosDto {

    private Integer envios;
    private Timestamp ultimoEnvio;

    private Integer recebidos;
    private Timestamp ultimoRecebido;

    private Integer total;

    public TransacaoInfosDto() {
    }

    public Integer getEnvios() {
        return envios;
    }

    public void setEnvios(Integer envios) {
        this.envios = envios;
    }

    public Timestamp getUltimoEnvio() {
        return ultimoEnvio;
    }

    public void setUltimoEnvio(Timestamp ultimoEnvio) {
        this.ultimoEnvio = ultimoEnvio;
    }

    public Integer getRecebidos() {
        return recebidos;
    }

    public void setRecebidos(Integer recebidos) {
        this.recebidos = recebidos;
    }

    public Timestamp getUltimoRecebido() {
        return ultimoRecebido;
    }

    public void setUltimoRecebido(Timestamp ultimoRecebido) {
        this.ultimoRecebido = ultimoRecebido;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
