package ar.edu.unq.spring.exception;

public class ListaExistenteException extends RuntimeException {
    public ListaExistenteException() {
        super("el nombre de esta lista ya existe");
    }
}
