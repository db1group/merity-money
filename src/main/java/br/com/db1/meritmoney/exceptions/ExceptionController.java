package br.com.db1.meritmoney.exceptions;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.processing.FilerException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<StandartError> saldoInsuficiente(SaldoInsuficienteException e, HttpServletRequest request) {
        StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Saldo insuficiente", e.getMessage(), request.getRequestURI());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandartError> authorization(AuthorizationException e, HttpServletRequest request) {
        StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Permissões insuficientes", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<StandartError> senhaInvalida(AuthorizationException e, HttpServletRequest request) {
        StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Senha inválida", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<StandartError> emailJaCadastrado(AuthorizationException e, HttpServletRequest request) {
        StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.CONFLICT.value(), "Email em uso", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validationError(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError error = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", e.getMessage(), request.getRequestURI());
        for (FieldError fe : e.getBindingResult().getFieldErrors()) {
            error.addError(fe.getField(), fe.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(FilerException.class)
    public ResponseEntity<StandartError> fileException(FilerException e, HttpServletRequest request) {
        StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Falha no upload", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<StandartError> loginFailure(FilerException e, HttpServletRequest request) {
        StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Usuário ou senha inválida", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(DestinatarioInvalidoException.class)
    public ResponseEntity<StandartError> invalidDestinatario(FilerException e, HttpServletRequest request) {
        StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Usuário destinatário inválido", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RemetenteInvalidoException.class)
    public ResponseEntity<StandartError> invalidRemetente(FilerException e, HttpServletRequest request) {
        StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Usuário remetente inválido", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
