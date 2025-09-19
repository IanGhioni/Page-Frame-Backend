package ar.edu.unq.spring.controller.dto;

public record ContenidoDeUsuarioSimpleResponseDTO(Long id, String estado, Long contenidoId, String contenidoNombre) {
    public static ContenidoDeUsuarioSimpleResponseDTO fromModel(ar.edu.unq.spring.modelo.ContenidoDeUsuario contenidoDeUsuario) {
        return new ContenidoDeUsuarioSimpleResponseDTO(
                contenidoDeUsuario.getId(),
                contenidoDeUsuario.getEstado(),
                contenidoDeUsuario.getContenido().getId(),
                contenidoDeUsuario.getContenido().getTitulo()
        );
    }
}