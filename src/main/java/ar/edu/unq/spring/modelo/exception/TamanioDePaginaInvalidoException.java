package ar.edu.unq.spring.modelo.exception;

public class TamanioDePaginaInvalidoException extends RuntimeException {
    public TamanioDePaginaInvalidoException() {
        super("Tamaño de pagina invalido.");
    }
}
