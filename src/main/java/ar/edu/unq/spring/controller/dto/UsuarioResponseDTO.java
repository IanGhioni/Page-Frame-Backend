package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.ContenidoDeUsuario;
import ar.edu.unq.spring.modelo.Usuario;

import java.util.List;
import java.util.Set;

public record UsuarioResponseDTO(Long id, String username, String email, List<ContenidoDeUsuarioSimpleResponseDTO> contenidos, List<ContenidoDeUsuarioPersonalizadoSimpleResponseDTO> contenidoPersonalizado, String fotoPerfil) {
    public static UsuarioResponseDTO fromModel(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(), usuario.getEmail(), usuario.getMisContenidos().stream()
                .map(ContenidoDeUsuarioSimpleResponseDTO::fromModel)
                .toList(), usuario.getListasPersonalizadas().stream().map(ContenidoDeUsuarioPersonalizadoSimpleResponseDTO::fromModel).toList() ,usuario.getFotoPerfil());
    }
}