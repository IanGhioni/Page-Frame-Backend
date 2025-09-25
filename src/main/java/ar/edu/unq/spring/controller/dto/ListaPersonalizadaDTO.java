package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.ContenidoDeUsuarioPersonalizado;

import java.util.List;
import java.util.Set;

public record ListaPersonalizadaDTO(List<ContenidoResponseDTO> contenidos, String nombre, String descripcion) {
    public static ListaPersonalizadaDTO fromModel(Set<ContenidoResponseDTO> contenidos, String nombre, String descripcion) {
        return new ListaPersonalizadaDTO(contenidos.stream().toList(), nombre, descripcion);
    }
}
