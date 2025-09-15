package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Contenido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContenidoBodyDTO (String isbn,
    @NotBlank(message = "Debe proporcionar una URL de imagen") String imagen,
    @NotNull(message = "El título no puede ser vacío") String titulo,
    @NotNull(message = "El campo autores no puede ser vacío") String autores,
    @NotNull(message = "El año de publicación no puede ser vacío") int publicacion,
    @NotBlank(message = "La descripción no puede ser vacía") String descripcion,
    @NotBlank(message = "La categoría no puede ser vacía") String categoria,
    @NotNull(message = "El largo no puede ser vacío") int largo) {

        public static Contenido aModelo(ContenidoBodyDTO contenidoBodyDTO) {
            return new Contenido(
                    contenidoBodyDTO.isbn,
                    contenidoBodyDTO.imagen,
                    contenidoBodyDTO.titulo,
                    contenidoBodyDTO.autores,
                    contenidoBodyDTO.publicacion,
                    contenidoBodyDTO.descripcion,
                    contenidoBodyDTO.categoria,
                    0,
                    0,
                    contenidoBodyDTO.largo
            );
        }
}
