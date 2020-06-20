package br.com.db1.meritmoney.exceptions;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String message) {
        super(message);
    }

    public EmailJaCadastradoException() {
        super("Email já cadastrado");
    }
}
