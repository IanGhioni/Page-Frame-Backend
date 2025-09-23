package ar.edu.unq.spring.exception;

public class NroDePaginaInvalidoException extends RuntimeException {
    public NroDePaginaInvalidoException() {
        super("Número de página inválido");
    }
}
