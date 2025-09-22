package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.ContenidoDeUsuario;
import ar.edu.unq.spring.modelo.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario crear(Usuario usuario);
    Usuario recuperar(Long usuarioId);
    void actualizar(Usuario usuario);
    void eliminar(Usuario usuario);
    Usuario recuperarPorUsername(String username);
    void agregarContenidoAUsuario(Long usuarioId, Long contenidoId, String estado);
    void eliminarContenidoDeUsuario(Long usuarioId, Long contenidoId);
    List<ContenidoDeUsuario> getContenidosDeUsuarioConEstado(Long usuarioId, String estado);
}
