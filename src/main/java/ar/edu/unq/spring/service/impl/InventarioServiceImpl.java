package ar.edu.unq.spring.service.impl;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.persistence.ItemDAO;
import ar.edu.unq.spring.persistence.PersonajeDAO;
import ar.edu.unq.spring.service.interfaces.InventarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class InventarioServiceImpl implements InventarioService {
    private final PersonajeDAO personajeDAO;
    private final ItemDAO itemDAO;
    public InventarioServiceImpl(PersonajeDAO personajeDAO, ItemDAO itemDAO) {
        this.personajeDAO = personajeDAO;
        this.itemDAO = itemDAO;
    }

    @Override
    public Item getItem(Long itemId) {
        return itemDAO.findById(itemId).orElseThrow(() -> new NoSuchElementException("Item not found with id: " + itemId));
    }

    @Override
    public Set<Item> allItems() {
        return Set.copyOf(itemDAO.findAll());
    }

    @Override
    public Item heaviestItem() {
        return itemDAO.findTopByOrderByPesoDesc();
    }

    @Override
    public Item guardarItem(Item item) {
        return itemDAO.save(item);
    }

    @Override
    public void recoger(Long personajeId, Long itemId) {
        Personaje personaje = personajeDAO.findById(personajeId)
                .orElseThrow(() -> new NoSuchElementException("Personaje not found with id: " + personajeId));

        Item item = itemDAO.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item not found with id: " + itemId));

        personaje.recoger(item);
        personajeDAO.save(personaje);
    }

    @Override
    public Set<Item> getMasPesados(int peso) {
        return itemDAO.getMasPesados(peso);
    }

    @Override
    public Set<Item> getItemsPersonajesDebiles(int vida) {
        return itemDAO.getItemsDePersonajesDebiles(vida);
    }

    @Override
    public void clearAll() {
        itemDAO.deleteAll();
    }

    @Override
    public void deleteItem(Item item) {
        itemDAO.delete(item);
    }
}