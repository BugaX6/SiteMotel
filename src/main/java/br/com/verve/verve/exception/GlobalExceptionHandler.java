package br.com.verve.verve.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErroResposta> tratarEmailDuplicado(EmailJaCadastradoException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErroResposta(exception.getMessage()));
    }    
}
