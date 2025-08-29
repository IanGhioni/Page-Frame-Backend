package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Personaje;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record PersonajeDTO(Long id, String nombre, int vida, int pesoMaximo, Set<ItemDTO> inventario) {

    public static PersonajeDTO desdeModelo(Personaje personaje) {
        return new PersonajeDTO(
                personaje.getId(),
                personaje.getNombre(),
                personaje.getVida(),
                personaje.getPesoMaximo(),
                personaje.getInventario().stream()
                        .map(ItemDTO::desdeModelo)
                        .collect(Collectors.toCollection(HashSet::new))
        );
    }

    public Personaje aModelo() {
        Personaje personaje = new Personaje(this.nombre, this.vida, this.pesoMaximo);
        personaje.setId(this.id);
        personaje.setInventario(this.inventario != null ?
                this.inventario.stream()
                        .map(itemDTO -> itemDTO.aModelo(personaje))
                        .collect(Collectors.toCollection(HashSet::new)) :
                new HashSet<>()
        );
        return personaje;
    }
}