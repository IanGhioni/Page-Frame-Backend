package ar.edu.unq.spring.exception;

public class TamanioDePaginaInvalidoException extends RuntimeException {
    public TamanioDePaginaInvalidoException() {
        super("Tamaño de página inválido");
    }
}
