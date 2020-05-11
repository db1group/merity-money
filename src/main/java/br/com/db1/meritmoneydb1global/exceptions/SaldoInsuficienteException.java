package br.com.db1.meritmoneydb1global.exceptions;

public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException() {
        super("Permissões insuficientes");
    }

    public SaldoInsuficienteException(String message, Throwable cause) {
        super(message, cause);
    }
}
