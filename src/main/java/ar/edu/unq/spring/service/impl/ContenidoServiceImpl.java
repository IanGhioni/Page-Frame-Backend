package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Contenido;
import ar.edu.unq.spring.modelo.exception.ContenidoNoEncontradoException;
import ar.edu.unq.spring.persistence.ContenidoDAO;
import ar.edu.unq.spring.service.interfaces.ContenidoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContenidoServiceImpl implements ContenidoService {
    private final ContenidoDAO contenidoDAO;

    public ContenidoServiceImpl(ContenidoDAO contenidoDAO) {
        this.contenidoDAO = contenidoDAO;
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
}
