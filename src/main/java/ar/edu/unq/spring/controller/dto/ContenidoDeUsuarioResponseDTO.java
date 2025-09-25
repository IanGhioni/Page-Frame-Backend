package ar.edu.unq.spring.controller.dto;

import java.util.List;

public record ContenidoDeUsuarioResponseDTO(Long idUser, String estado, Long id, String imagen, String titulo, String autores, Double ratingAverage, Double ratingCount, int publicacion, String isbn, List<ReviewResponseDTO> reviews) {
    public static ContenidoDeUsuarioResponseDTO fromModel(ar.edu.unq.spring.modelo.ContenidoDeUsuario contenidoDeUsuario) {
        return new ContenidoDeUsuarioResponseDTO(
                contenidoDeUsuario.getId(),
                contenidoDeUsuario.getEstado(),
                contenidoDeUsuario.getContenido().getId(),
                contenidoDeUsuario.getContenido().getImagen(),
                contenidoDeUsuario.getContenido().getTitulo(),
                contenidoDeUsuario.getContenido().getAutores(),
                contenidoDeUsuario.getContenido().getRatingAverage(),
                contenidoDeUsuario.getContenido().getRatingAverage(),
                contenidoDeUsuario.getContenido().getPublicacion(),
                contenidoDeUsuario.getContenido().getIsbn(),
                contenidoDeUsuario.getContenido().getReviews().stream().map(ReviewResponseDTO::fromModel).toList()
        );
    }
}
