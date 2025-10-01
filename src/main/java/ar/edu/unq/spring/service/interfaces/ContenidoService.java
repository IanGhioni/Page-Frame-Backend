package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Contenido;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ContenidoService {
    Contenido crear(Contenido contenido);
    Optional<Contenido> recuperar(Long contenidoId);
    List<Contenido> recuperarTodos();
    List<Contenido> recuperarTodoOrdenadoPorTituloAsc();
    List<Contenido> recuperarTodoOrdenadoPorAutoresAsc();
    void actualizar(Contenido contenido);
    void eliminar(Contenido contenido);
    Page<Contenido> recuperarPorNombre(String nombre, int pagina, int tamanioPorPagina);
    Page<Contenido> explorarContenidoPopular(int pagina, int tamanioPorPagina);
    void valorarContenido(Long contenidoId, Double valoracion, Long usuarioId);
    void eliminarValoracionContenido(Long contenidoId, Long usuarioId);
    Page<Contenido> recuperarPorNombreDeAutores(String nombre, int nroPagina, int tamanioPorPagina);
    void escribirReview(Long contenidoId, Long usuarioId, String text);
}