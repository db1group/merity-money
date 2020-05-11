package br.com.db1.meritmoneydb1global.exceptions;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        super("Permissões insuficientes");
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
