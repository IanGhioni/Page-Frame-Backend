package ar.edu.unq.spring.controller.dto;

public record UsuarioResponseDTO (Long id, String username, String email) {
    public static UsuarioResponseDTO fromModel(ar.edu.unq.spring.modelo.Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(), usuario.getEmail());
    }
}
