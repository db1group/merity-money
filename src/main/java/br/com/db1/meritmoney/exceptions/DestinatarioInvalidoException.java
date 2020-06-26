package br.com.db1.meritmoney.exceptions;

public class DestinatarioInvalidoException extends RuntimeException {

    public DestinatarioInvalidoException() {
        super("Não é permitido envio de Merit Money para si mesmo");
    }
}
