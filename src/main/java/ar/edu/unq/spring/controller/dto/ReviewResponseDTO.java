package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Review;

public record ReviewResponseDTO(Long id, Long usuarioId, Long contenidoId, Double valoracion) {
    public static ReviewResponseDTO fromModel(Review review) {
        return new ReviewResponseDTO( review.getId(), review.getUsuario().getId(), review.getContenido().getId(),  review.getValoracion() );
    }
}
