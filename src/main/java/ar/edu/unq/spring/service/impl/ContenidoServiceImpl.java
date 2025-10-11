package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.exception.*;
import ar.edu.unq.spring.modelo.Contenido;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.persistence.ContenidoDAO;
import ar.edu.unq.spring.service.interfaces.ContenidoService;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ContenidoServiceImpl implements ContenidoService {
    private final ContenidoDAO contenidoDAO;
    private final UsuarioService usuarioService;

    public ContenidoServiceImpl(ContenidoDAO contenidoDAO, UsuarioService usuarioService) {
        this.contenidoDAO = contenidoDAO;
        this.usuarioService = usuarioService;
    }

    @Override
    public Contenido crear(Contenido contenido) {
        return this.contenidoDAO.save(contenido);
    }

    @Override
    public Optional<Contenido> recuperar(Long contenidoId) {
        return this.contenidoDAO.findById(contenidoId);
    }

    @Override
    public List<Contenido> recuperarTodos() {
        return this.contenidoDAO.findAll();
    }

    @Override
    public List<Contenido> recuperarTodoOrdenadoPorTituloAsc() {
        return this.contenidoDAO.contenidoOrdPorTituloAsc();
    }

    @Override
    public List<Contenido> recuperarTodoOrdenadoPorAutoresAsc() {
        return this.contenidoDAO.contenidoOrdPorAutoresAsc();
    }

    @Override
    public void actualizar(Contenido contenido) {
        if (contenido.getId() == null) {
            throw new ContenidoNoEncontradoException();
        }
        this.contenidoDAO.save(contenido);
    }

    @Override
    public void eliminar(Contenido contenido) {
        if(contenido.getId() == null) {
            throw new ContenidoNoEncontradoException();
        }
        contenidoDAO.delete(contenido);
    }

    @Override
    public Page<Contenido> recuperarPorNombre(String nombre, int nroPagina, int tamanioPorPagina) {
        this.validarPaginacion(nroPagina, tamanioPorPagina);

        PageRequest p = PageRequest.of(nroPagina, tamanioPorPagina, Sort.by("titulo").ascending());
        Page<Contenido> page = this.contenidoDAO.findByTituloContainingRelavance(nombre, p);

        return page;
    }

    @Override
    public Page<Contenido> recuperarPorNombreDeAutores(String autor, int nroPagina, int tamanioPorPagina) {
        this.validarPaginacion(nroPagina, tamanioPorPagina);

        PageRequest p = PageRequest.of(nroPagina, tamanioPorPagina, Sort.by("autores").ascending());
        Page<Contenido> page = this.contenidoDAO.findByAutores(autor, p);

        return page;
    }

    @Override
    public Page<Contenido> explorarContenidoPopular(int nroPagina, int tamanioPorPagina) {
        this.validarPaginacion(nroPagina, tamanioPorPagina);

        PageRequest p = PageRequest.of(nroPagina, tamanioPorPagina, Sort.by("ratingCount").descending());
        Page<Contenido> page = this.contenidoDAO.contenidoOrdPorRatingCount(p);

        return page;
    }

    @Override
    public void valorarContenido(Long contenidoId, Double valoracion, Long usuarioId) {
        Contenido contenido = this.contenidoDAO.findById(contenidoId)
                .orElseThrow(ContenidoNoEncontradoException::new);
        Usuario usuario = this.usuarioService.recuperar(usuarioId);

        contenido.agregarOActualizarValoracion(usuario, valoracion);
        this.contenidoDAO.save(contenido);
    }

    @Override
    public void escribirReview(Long contenidoId, Long usuarioId, String texto) {
        if (texto == null || texto.isEmpty()) {
            throw new CuerpoDeReviewInvalido();
        }

        Contenido contenido = this.contenidoDAO.findById(contenidoId)
                .orElseThrow(ContenidoNoEncontradoException::new);
        Usuario usuario = this.usuarioService.recuperar(usuarioId);

        try {
            contenido.agregarOActualizarReview(usuario, texto);
        } catch (NoSuchElementException e) {
            throw new ReviewSinValoracion();
        }
        this.contenidoDAO.save(contenido);
    }

    @Override
    public void eliminarValoracionContenido(Long contenidoId, Long usuarioId) {
        Contenido contenido = this.contenidoDAO.findById(contenidoId
        ).orElseThrow(ContenidoNoEncontradoException::new);
        Usuario usuario = this.usuarioService.recuperar(usuarioId);
        contenido.eliminarValoracion(usuario);
        this.contenidoDAO.save(contenido);

    }

    public void validarPaginacion(int nroPagina, int tamanioPorPagina) {
        if (nroPagina < 0) {
            throw new NroDePaginaInvalidoException();
        }
        if (tamanioPorPagina < 1) {
            throw new TamanioDePaginaInvalidoException();
        }
    }

    @Override
    public void eliminarReview(Long contenidoId, Long usuarioId) {
        Contenido contenido = this.contenidoDAO.findById(contenidoId)
                .orElseThrow(ContenidoNoEncontradoException::new);
        Usuario usuario = this.usuarioService.recuperar(usuarioId);

       contenido.eliminarTextoReview(usuario);

        this.contenidoDAO.save(contenido);
    }

    @Override
    public Page<Contenido> recuperarPorNombreSoloLibros(String nombre, int nroPagina, int tamanioPorPagina) {
        this.validarPaginacion(nroPagina, tamanioPorPagina);
        PageRequest p = PageRequest.of(nroPagina, tamanioPorPagina, Sort.by("titulo").ascending());
        Page<Contenido> page = this.contenidoDAO.findByTituloOnlyBooks(nombre, p);

        return page;
    }

    @Override
    public Page<Contenido> recuperarPorNombreSoloPeliculas(String nombre, int nroPagina, int tamanioPorPagina) {
        this.validarPaginacion(nroPagina, tamanioPorPagina);
        PageRequest p = PageRequest.of(nroPagina, tamanioPorPagina, Sort.by("titulo").ascending());
        Page<Contenido> page = this.contenidoDAO.findByTituloOnlyMovies(nombre, p);

        return page;
    }

    @Override
    public Page<Contenido> recuperarPorAutorSoloLibros(String autor, int nroPagina, int tamanioPorPagina) {
        this.validarPaginacion(nroPagina, tamanioPorPagina);

        PageRequest p = PageRequest.of(nroPagina, tamanioPorPagina, Sort.by("autores").ascending());
        Page<Contenido> page = this.contenidoDAO.findByAutorOnlyBooks(autor, p);

        return page;
    }

    @Override
    public Page<Contenido> recuperarPorAutorSoloPeliculas(String autor, int nroPagina, int tamanioPorPagina) {
        this.validarPaginacion(nroPagina, tamanioPorPagina);

        PageRequest p = PageRequest.of(nroPagina, tamanioPorPagina, Sort.by("autores").ascending());
        Page<Contenido> page = this.contenidoDAO.findByAutorOnlyMovies(autor, p);

        return page;
    }
}
