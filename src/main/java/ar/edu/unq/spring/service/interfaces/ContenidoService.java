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
    void eliminarReview(Long contenidoId, Long usuarioId);
    void editarTextoReview(Long contenidoId, Long usuarioId, String nuevoTexto);
    String getTextoReview(Long contenidoId, Long usuarioId);

    Page<Contenido> recuperarPorNombreSoloLibros(String nombre, int nroPagina, int tamanioPorPagina);
    Page<Contenido> recuperarPorNombreSoloPeliculas(String nombre, int nroPagina, int tamanioPorPagina);
    Page<Contenido> recuperarPorAutorSoloLibros(String autor, int nroPagina, int tamanioPorPagina);
    Page<Contenido> recuperarPorAutorSoloPeliculas(String autor, int nroPagina, int tamanioPorPagina);

    Page<Contenido> recuperarPorGenero(String genero, int nroPagina, int tamanioPorPagina);
    Page<Contenido> recuperarPorGeneroSoloLibros(String genero, int nroPagina, int tamanioPorPagina);
    Page<Contenido> recuperarPorGeneroSoloPeliculas(String genero, int nroPagina, int tamanioPorPagina);
}