package ar.edu.unq.spring.modelo.exception;

public class ContenidoNoEncontrado extends RuntimeException {
    public ContenidoNoEncontrado() {
        super("El contenido no fue encontrado");
    }
}
