package ar.edu.unq.spring.modelo.exception;

public class ContenidoNoEncontradoException extends RuntimeException {
    public ContenidoNoEncontradoException() {
        super("Contenido no encontrado");
    }
}
