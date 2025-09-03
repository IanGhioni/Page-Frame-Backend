package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Contenido;

import java.util.List;
import java.util.Optional;

public interface ContenidoService {
    Contenido crear(Contenido contenido);
    Optional<Contenido> recuperar(Long contenidoId);
    List<Contenido> recuperarTodos();
    void actualizar(Contenido contenido);
    void eliminar(Contenido contenido);
}