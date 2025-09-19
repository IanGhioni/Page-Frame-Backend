package ar.edu.unq.spring.modelo.exception;

public class UsuarioNoEncontrado extends RuntimeException {
    public UsuarioNoEncontrado() {
        super("El usuario no fue encontrado");
    }
}
