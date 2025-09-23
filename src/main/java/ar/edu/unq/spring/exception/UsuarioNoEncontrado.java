package ar.edu.unq.spring.exception;

public class UsuarioNoEncontrado extends RuntimeException {
    public UsuarioNoEncontrado() {
        super("Usuario no encontrado");
    }
}
