package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Contenido;
import ar.edu.unq.spring.modelo.ContenidoDeUsuario;
import ar.edu.unq.spring.modelo.ContenidoDeUsuarioPersonalizado;
import ar.edu.unq.spring.modelo.Usuario;

import java.util.List;
import java.util.Set;

public interface UsuarioService {
    Usuario crear(Usuario usuario);
    Usuario recuperar(Long usuarioId);
    void actualizar(Usuario usuario);
    void eliminar(Usuario usuario);
    Usuario recuperarPorUsername(String username);
    void agregarContenidoAUsuario(Long usuarioId, Long contenidoId, String estado);
    void eliminarContenidoDeUsuario(Long usuarioId, Long contenidoId);
    List<ContenidoDeUsuario> getContenidosDeUsuarioConEstado(Long usuarioId, String estado);
    void crearListaPersonalizada(Long usuarioId, String nombre, String descripcion);
    void agregarContenidoAListaPersonalizada(Long usuarioId, Long contenidoId, String nombre);
    Set<Contenido> getContenidosDeListaPersonalizada(Long usuarioId, String nombre);
    Set<ContenidoDeUsuarioPersonalizado> getListasPersonalizadasDeUsuario(Long usuarioId);
}
