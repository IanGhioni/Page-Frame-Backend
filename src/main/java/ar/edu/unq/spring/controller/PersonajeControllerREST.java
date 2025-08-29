package ar.edu.unq.spring.controller;

import ar.edu.unq.spring.controller.dto.PersonajeDTO;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.service.interfaces.PersonajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/personaje")
public final class PersonajeControllerREST {
    private final PersonajeService personajeService;

    public PersonajeControllerREST(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }

    @GetMapping
    public Set<PersonajeDTO> getAllPersonajes() {
        return personajeService.allPersonajes().stream()
                .map(PersonajeDTO::desdeModelo)
                .collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonajeDTO> getPersonajeById(@PathVariable Long id) {
        Personaje personaje = personajeService.recuperarPersonaje(id);
        return ResponseEntity.ok(PersonajeDTO.desdeModelo(personaje));
    }

    @PostMapping
    public void createPersonaje(@RequestBody PersonajeDTO personaje) {
        personajeService.guardarPersonaje(personaje.aModelo());
    }
}