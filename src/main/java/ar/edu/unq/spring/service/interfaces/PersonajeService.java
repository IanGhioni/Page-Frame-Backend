package ar.edu.unq.spring.service.interfaces;

import ar.edu.unq.spring.modelo.Personaje;

import java.util.Set;

public interface PersonajeService {
    Set<Personaje> allPersonajes();
    void guardarPersonaje(Personaje personaje);
    Personaje recuperarPersonaje(Long personajeId);
    void clearAll();
}