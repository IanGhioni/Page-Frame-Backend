package ar.edu.unq.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ContenidoNoEncontradoException.class)
    public ResponseEntity<String> handleContenidoNoEncontrado(ContenidoNoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NroDePaginaInvalidoException.class)
    public ResponseEntity<String> handleNroDePaginaInvalido(NroDePaginaInvalidoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TamanioDePaginaInvalidoException.class)
    public ResponseEntity<String> handleTamanioDePaginaInvalido(TamanioDePaginaInvalidoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsuarioNoEncontrado.class)
    public ResponseEntity<String> handleUsuarioNoEncontrado(UsuarioNoEncontrado ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}