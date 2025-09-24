package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.ContenidoDeUsuarioPersonalizado;

import java.util.Set;

public record ListaPersonalizadaDTO(Set<ContenidoBodyDTO> contenidos, String nombre, String descripcion) {
    public static ListaPersonalizadaDTO fromModel(Set<ContenidoBodyDTO> contenidos, String nombre, String descripcion) {
        return new ListaPersonalizadaDTO(contenidos, nombre, descripcion);
    }
}
