package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.ItemDTO;
import ar.edu.unq.spring.service.interfaces.InventarioService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/inventario")
final public class InventarioControllerREST {
    private final InventarioService inventarioService;
    public InventarioControllerREST(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/all")
    public Set<ItemDTO> allItems() {
        return inventarioService.allItems().stream()
                .map(ItemDTO::desdeModelo)
                .collect(Collectors.toSet());
    }

    @GetMapping("/itemMasPesado")
    public ItemDTO heaviestItem() {
        return ItemDTO.desdeModelo(inventarioService.heaviestItem());
    }

    @GetMapping("/masPesados/{peso}")
    public Set<ItemDTO> getMasPesados(@PathVariable int peso) {
        return inventarioService.getMasPesados(peso).stream()
                .map(ItemDTO::desdeModelo)
                .collect(Collectors.toSet());
    }

    @GetMapping("/itemsPersonajesDebiles/{vida}")
    public Set<ItemDTO> getItemsPersonajesDebiles(@PathVariable int vida) {
        return inventarioService.getItemsPersonajesDebiles(vida).stream()
                .map(ItemDTO::desdeModelo)
                .collect(Collectors.toSet());
    }

    @PostMapping
    public void guardarItem(@RequestBody ItemDTO item) {
        inventarioService.guardarItem(item.aModelo());
    }

    @PutMapping("/personaje/{personajeId}/recoger/item/{itemId}")
    public void recoger(@PathVariable Long personajeId, @PathVariable Long itemId) {
        inventarioService.recoger(personajeId, itemId);
    }
}