package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.modelo.exception.NombreDePersonajeRepetido;
import ar.edu.unq.spring.persistence.PersonajeDAO;
import ar.edu.unq.spring.service.interfaces.PersonajeService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Transactional
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeDAO personajeDAO;
    public PersonajeServiceImpl(PersonajeDAO personajeDAO) {
        this.personajeDAO = personajeDAO;
    }

    @Override
    public Set<Personaje> allPersonajes() {
        return Set.copyOf(personajeDAO.findAll());
    }

    @Override
    public void guardarPersonaje(Personaje personaje) {
        try {
            personajeDAO.save(personaje);
        } catch (DataIntegrityViolationException e) {
            throw new NombreDePersonajeRepetido(personaje.getNombre());
        }
    }

    @Override
    public Personaje recuperarPersonaje(Long personajeId) {
        return personajeDAO.findById(personajeId).orElseThrow(() -> new NoSuchElementException("Personaje not found with id: " + personajeId));
    }

    @Override
    public void clearAll() {
        personajeDAO.deleteAll();
    }
}