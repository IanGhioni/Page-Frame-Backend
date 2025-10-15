package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.controller.dto.ListaPersonalizadaDTO;
import ar.edu.unq.spring.exception.ContenidoNoEncontradoException;
import ar.edu.unq.spring.exception.ListaExistenteException;
import ar.edu.unq.spring.modelo.Contenido;
import ar.edu.unq.spring.modelo.ContenidoDeUsuario;
import ar.edu.unq.spring.modelo.ContenidoDeUsuarioPersonalizado;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.exception.UsuarioNoEncontrado;
import ar.edu.unq.spring.persistence.ContenidoDAO;
import ar.edu.unq.spring.persistence.ContenidoDeUsuarioDAO;
import ar.edu.unq.spring.persistence.ContenidoDeUsuarioPersonalizadoDAO;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    private final ContenidoDAO contenidoDAO;
    private final UsuarioDAO usuarioDAO;
    private final ContenidoDeUsuarioDAO contenidoDeUsuarioDAO;
    private final ContenidoDeUsuarioPersonalizadoDAO contenidoDeUsuarioPersonalizadoDAO;

    public UsuarioServiceImpl(ContenidoDAO contenidoDAO, UsuarioDAO usuarioDAO, ContenidoDeUsuarioDAO contenidoDeUsuarioDAO, ContenidoDeUsuarioPersonalizadoDAO contenidoDeUsuarioPersonalizadoDAO) {
        this.contenidoDAO = contenidoDAO;
        this.usuarioDAO = usuarioDAO;
        this.contenidoDeUsuarioDAO = contenidoDeUsuarioDAO;
        this.contenidoDeUsuarioPersonalizadoDAO = contenidoDeUsuarioPersonalizadoDAO;
    }

    @Override
    public Usuario crear(Usuario usuario) {
        return this.usuarioDAO.save(usuario);
    }

    @Override
    public Usuario recuperar(Long usuarioId) {
        return this.usuarioDAO.findById(usuarioId).orElseThrow(UsuarioNoEncontrado::new);
    }

    @Override
    public void actualizar(Usuario usuario) {
        this.usuarioDAO.findById(usuario.getId()).orElseThrow(UsuarioNoEncontrado::new);
        this.usuarioDAO.save(usuario);
    }

    @Override
    public void eliminar(Usuario usuario) {
        this.usuarioDAO.findById(usuario.getId()).orElseThrow(UsuarioNoEncontrado::new);
        this.usuarioDAO.delete(usuario);
    }

    @Override
    public Usuario recuperarPorUsername(String username) {
        return this.usuarioDAO.findByUsername(username).orElseThrow(UsuarioNoEncontrado::new);
    }

    @Override
    public void agregarContenidoAUsuario(Long usuarioId, Long contenidoId, String estado) {
        Usuario usuario = this.recuperar(usuarioId);
        Contenido contenido = this.contenidoDAO.findById(contenidoId).get();

        ContenidoDeUsuario contenidoDeUsuario =
                contenidoDeUsuarioDAO.findByUsuarioIdAndContenidoId(usuario.getId(), contenido.getId());

        if (contenidoDeUsuario == null) {
            usuario.agregarContenido(contenido, estado);
        } else {
            usuario.actualizarContenido(contenido, estado);
        }

        usuarioDAO.save(usuario);
    }

    @Override
    public void eliminarContenidoDeUsuario(Long usuarioId, Long contenidoId) {
        Usuario usuario = this.recuperar(usuarioId);
        Contenido contenido = this.contenidoDAO.findById(contenidoId).get();

        ContenidoDeUsuario contenidoDeUsuario =
                contenidoDeUsuarioDAO.findByUsuarioIdAndContenidoId(usuario.getId(), contenido.getId());

        if (contenidoDeUsuario != null) {
            usuario.eliminarContenido(contenido);
            usuarioDAO.save(usuario);
        }
    }

    @Override
    public List<ContenidoDeUsuario> getContenidosDeUsuarioConEstado(Long usuarioId, String estado) {
        this.recuperar(usuarioId);
        return contenidoDeUsuarioDAO.findByUsuarioIdAndEstado(usuarioId, estado);
    }

    @Override
    public void crearListaPersonalizada(Long usuarioId, String nombre, String descripcion) {
        Usuario usuario = usuarioDAO.findById(usuarioId).orElseThrow(UsuarioNoEncontrado::new);
        if (usuario.yaExisteListaDeNombre(nombre)) {
            throw new ListaExistenteException();
        } else {
            usuario.agregarListaPersonalizada(nombre, descripcion);
            usuarioDAO.save(usuario);
        }
    }
    @Override
    public ContenidoDeUsuarioPersonalizado getListaPersonalizada(Long usuarioId, String nombre) {
        Usuario usuario = usuarioDAO.findById(usuarioId).orElseThrow(UsuarioNoEncontrado::new);
        return usuario.getLista(nombre);
    }

    @Override
    public void agregarContenidoAListaPersonalizada(Long usuarioId, Long contenidoId, String nombre){
        Usuario usuario = usuarioDAO.findById(usuarioId).orElseThrow(UsuarioNoEncontrado::new);
        Contenido contenido = this.contenidoDAO.findById(contenidoId).get();

        ContenidoDeUsuarioPersonalizado contenidoDeUsuarioPersonalizado =
                contenidoDeUsuarioPersonalizadoDAO.findByUsuarioIdAndNombre(usuario.getId(), nombre);
        usuario.agregarContenidoAListaPersonalizada(contenidoDeUsuarioPersonalizado, contenido);
        usuarioDAO.save(usuario);
    }

    @Override
    public Set<Contenido> getContenidosDeListaPersonalizada(Long usuarioId, String nombre) {
        ContenidoDeUsuarioPersonalizado lista = contenidoDeUsuarioPersonalizadoDAO.findByUsuarioIdAndNombre(usuarioId, nombre);
        return lista.getContenido();
    }

    @Override
    public Set<ContenidoDeUsuarioPersonalizado> getListasPersonalizadasDeUsuario(Long usuarioId) {
        Usuario usuario = this.recuperar(usuarioId);
        return usuario.getListasPersonalizadas();
    }

    @Override
    public void eliminarContenidoDeListaPersonalizada(Long usuarioId, Long contenidoId, String nombre) {
        Usuario usuario = this.recuperar(usuarioId);
        Contenido contenido = this.contenidoDAO.findById(contenidoId).orElseThrow(ContenidoNoEncontradoException::new);

        ContenidoDeUsuarioPersonalizado lista = contenidoDeUsuarioPersonalizadoDAO.findByUsuarioIdAndNombre(usuarioId, nombre);

        lista.getContenido().remove(contenido);
        usuarioDAO.save(usuario);

    }

    @Override
    public void eliminarListaPersonalizada(Long usuarioId, String nombre) {
        Usuario usuario = this.recuperar(usuarioId);
        usuario.eliminarListaPersonalizada(nombre);
    }

}
