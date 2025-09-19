package ar.edu.unq.spring.controller.dto;

public record ContenidoDeUsuarioResponseDTO(Long idUser, String estado, Long id, String imagen,String titulo, String autores, Double ratingAverage, Double ratingCount, int publicacion, String isbn) {
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
                contenidoDeUsuario.getContenido().getIsbn()
        );
    }
}
