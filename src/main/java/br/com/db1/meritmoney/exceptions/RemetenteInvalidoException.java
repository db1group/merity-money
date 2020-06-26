package br.com.db1.meritmoney.exceptions;

public class RemetenteInvalidoException extends RuntimeException {

    public RemetenteInvalidoException() {
        super("Remetente inválido");
    }
}
