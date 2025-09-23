package ar.edu.unq.spring.exception;

public class ContenidoNoEncontradoException extends RuntimeException {
    public ContenidoNoEncontradoException() {
        super("Contenido no encontrado");
    }
}
