package ar.edu.unq.spring.modelo.exception;

public class NroDePaginaInvalidoException extends RuntimeException {
    public NroDePaginaInvalidoException() {
        super("El numero de pagina es invalido.");
    }
}
