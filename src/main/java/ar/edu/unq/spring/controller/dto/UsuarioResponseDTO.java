package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Usuario;

import java.util.List;

public record UsuarioResponseDTO (Long id, String username, String email, List<ContenidoDeUsuarioSimpleResponseDTO> contenidos) {
    public static UsuarioResponseDTO fromModel(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(), usuario.getEmail(), usuario.getMisContenidos().stream()
                .map(ContenidoDeUsuarioSimpleResponseDTO::fromModel)
                .toList());
    }
}
