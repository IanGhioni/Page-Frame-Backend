package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Review;

public record ReviewResponseDTO(Long id, Long usuarioId, String username, String userPhoto, Long contenidoId, Double valoracion, String texto) {
    public static ReviewResponseDTO fromModel(Review review) {
        return new ReviewResponseDTO( review.getId(), review.getUsuario().getId(), review.getUsuario().getUsername(), review.getUsuario().getFotoPerfil(),  review.getContenido().getId(),  review.getValoracion(), review.getTexto());
    }
}
