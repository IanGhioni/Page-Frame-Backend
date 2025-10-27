package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.ContenidoDeUsuarioPersonalizado;

import java.util.List;

public record ContenidoDeUsuarioPersonalizadoSimpleResponseDTO(Long id, Long usuarioId , String nombre, String descripcion, List<ContenidoResponseDTO> contenidos) {
    public static ContenidoDeUsuarioPersonalizadoSimpleResponseDTO fromModel(ContenidoDeUsuarioPersonalizado contenidoDeUsuarioPersonalizado) {
        return new ContenidoDeUsuarioPersonalizadoSimpleResponseDTO(
                contenidoDeUsuarioPersonalizado.getId(),
                contenidoDeUsuarioPersonalizado.getUsuario().getId(),
                contenidoDeUsuarioPersonalizado.getNombre(),
                contenidoDeUsuarioPersonalizado.getDescripcion(),
                contenidoDeUsuarioPersonalizado.getContenido().stream().map(ContenidoResponseDTO::desdeModelo).toList()
        );
    }
}
