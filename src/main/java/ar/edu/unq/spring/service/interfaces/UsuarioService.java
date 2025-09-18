package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Usuario;

public interface UsuarioService {
    Usuario crear(Usuario usuario);
    Usuario recuperar(Long usuarioId);
    void actualizar(Usuario usuario);
    void eliminar(Usuario usuario);
    Usuario recuperarPorUsername(String username);
    void agregarContenidoAUsuario(Long usuarioId, Long contenidoId, String estado);
}
