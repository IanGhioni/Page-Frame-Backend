package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Contenido;
import ar.edu.unq.spring.modelo.ContenidoDeUsuario;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.modelo.exception.UsuarioNoEncontrado;
import ar.edu.unq.spring.persistence.ContenidoDAO;
import ar.edu.unq.spring.persistence.ContenidoDeUsuarioDAO;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    private final ContenidoDAO contenidoDAO;
    private final UsuarioDAO usuarioDAO;
    private final ContenidoDeUsuarioDAO contenidoDeUsuarioDAO;

    public UsuarioServiceImpl(ContenidoDAO contenidoDAO, UsuarioDAO usuarioDAO, ContenidoDeUsuarioDAO contenidoDeUsuarioDAO) {
        this.contenidoDAO = contenidoDAO;
        this.usuarioDAO = usuarioDAO;
        this.contenidoDeUsuarioDAO = contenidoDeUsuarioDAO;
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
    public void actualizar(Usuario usuario) {
        this.usuarioDAO.findById(usuario.getId()).orElseThrow(UsuarioNoEncontrado::new);
        this.usuarioDAO.save(usuario);
    }

    @Override
    public void eliminar(Usuario usuario) {
        this.usuarioDAO.findById(usuario.getId()).orElseThrow(UsuarioNoEncontrado::new);
        this.usuarioDAO.delete(usuario);
    }
}
