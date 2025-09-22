package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Contenido;

import java.util.List;

public record ContenidoResponseDTO (Long id, String isbn, String imagen, String titulo, String autores, int publicacion, String descripcion,
                                    String categoria, double ratingCount, double ratingAverage, int largo, List<ReviewResponseDTO> reviews) {

    public static ContenidoResponseDTO desdeModelo(Contenido contenido) {
        return new ContenidoResponseDTO(
                contenido.getId(),
                contenido.getIsbn(),
                contenido.getImagen(),
                contenido.getTitulo(),
                contenido.getAutores(),
                contenido.getPublicacion(),
                contenido.getDescripcion(),
                contenido.getCategoria(),
                contenido.getRatingCount(),
                contenido.getRatingAverage(),
                contenido.getLargo(),
                contenido.getReviews().stream().map(ReviewResponseDTO::fromModel).toList()
        );
    }
}
