package ar.edu.unq.spring.controller.dto;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;

public record ItemDTO(Long id, String nombre, int peso, Long ownerId) {

    public static ItemDTO desdeModelo(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getNombre(),
                item.getPeso(),
                item.getOwner() != null ? item.getOwner().getId() : null
        );
    }

    public Item aModelo(Personaje personaje) {
        Item item = aModelo();
        item.setOwner(personaje);
        return item;
    }

    public Item aModelo() {
        Item item = new Item(this.nombre, this.peso);
        item.setId(this.id);
        return item;
    }
}