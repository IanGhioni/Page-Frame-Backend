package ar.edu.unq.spring.service;

import ar.edu.unq.spring.modelo.Item;
import ar.edu.unq.spring.modelo.Personaje;
import ar.edu.unq.spring.modelo.exception.MuchoPesoException;
import ar.edu.unq.spring.modelo.exception.NombreDePersonajeRepetido;
import ar.edu.unq.spring.service.interfaces.InventarioService;
import ar.edu.unq.spring.service.interfaces.PersonajeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@SpringBootTest
public class InventarioServiceTest {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private PersonajeService personajeService;

    private Personaje maguin;
    private Personaje debilucho;
    private Item baculo;
    private Item tunica;

    @BeforeEach
    public void prepare() {
        tunica = new Item("Tunica", 100);
        baculo = new Item("Baculo", 50);

        maguin = new Personaje("Maguin", 10, 70);
        debilucho = new Personaje("Debilucho", 1, 1000);

        inventarioService.guardarItem(tunica);
        inventarioService.guardarItem(baculo);
        personajeService.guardarPersonaje(maguin);
        personajeService.guardarPersonaje(debilucho);
    }

    @Test
    public void testRecoger() {
        inventarioService.recoger(maguin.getId(), baculo.getId());

        Personaje maguito = personajeService.recuperarPersonaje(maguin.getId());
        Assertions.assertEquals("Maguin", maguito.getNombre());
        Assertions.assertEquals(1, maguito.getInventario().size());

        Item baculo = maguito.getInventario().iterator().next();
        Assertions.assertEquals("Baculo", baculo.getNombre());
        Assertions.assertSame(baculo.getOwner(), maguito);
    }

    @Test
    public void testGetAll() {
        Collection<Item> items = inventarioService.allItems();
        Assertions.assertEquals(2, items.size());
        Assertions.assertTrue(items.contains(baculo));
    }

    @Test
    public void testGetMasPesados() {
        Collection<Item> items = inventarioService.getMasPesados(10);
        Assertions.assertEquals(2, items.size());

        Collection<Item> items2 = inventarioService.getMasPesados(80);
        Assertions.assertEquals(1, items2.size());
    }

    @Test
    public void testGetItemsDebiles() {
        Collection<Item> items = inventarioService.getItemsPersonajesDebiles(5);
        Assertions.assertEquals(0, items.size());

        inventarioService.recoger(maguin.getId(), baculo.getId());
        inventarioService.recoger(debilucho.getId(), tunica.getId());

        items = inventarioService.getItemsPersonajesDebiles(5);
        Assertions.assertEquals(1, items.size());
        Assertions.assertEquals("Tunica", items.iterator().next().getNombre());
    }

    @Test
    public void testGetMasPesado() {
        Item item = inventarioService.heaviestItem();
        Assertions.assertEquals("Tunica", item.getNombre());
    }

    @Test
    public void testMuchoPesoException() {
        inventarioService.recoger(maguin.getId(), baculo.getId());
        MuchoPesoException exception = Assertions.assertThrows(MuchoPesoException.class, () -> {
            inventarioService.recoger(maguin.getId(), tunica.getId());
        });
        Assertions.assertEquals("El personaje [Maguin] no puede recoger [Tunica] porque cagar mucho peso ya", exception.getMessage());
    }

    @Test
    public void testNombreDePersonajeTieneQueSerUnico() {
        Personaje otroMaguin = new Personaje("Maguin", 10, 70);

        NombreDePersonajeRepetido exception = Assertions.assertThrows(NombreDePersonajeRepetido.class, () -> {
            personajeService.guardarPersonaje(otroMaguin);
        });
        Assertions.assertEquals("El nombre de personaje [Maguin] ya esta siendo utilizado y no puede volver a crearse", exception.getMessage());
    }

    @Test
    public void testPersistEnCascadeAUnaDetachedEntityLanzaDetachedEntityItemException() {
        Item espada = new Item("Espada", 100);
        inventarioService.guardarItem(espada);

        Personaje otroMaguito = new Personaje("Shierke", 10, 70);

        otroMaguito.getInventario().add(espada);
        espada.setOwner(otroMaguito);

        InvalidDataAccessApiUsageException exception = Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            personajeService.guardarPersonaje(otroMaguito);
        });
        Assertions.assertEquals("detached entity passed to persist: ar.edu.unq.spring.modelo.Item", exception.getMessage());
    }

    @Test
    public void deleteTambienPasaADetachedUnaVezTerminadaUnaTransaccion() {
        Item espada = new Item("Espada", 100);
        inventarioService.guardarItem(espada);
        inventarioService.deleteItem(espada);

        Personaje otroMaguito = new Personaje("Shierke", 10, 70);

        otroMaguito.getInventario().add(espada);
        espada.setOwner(otroMaguito);

        InvalidDataAccessApiUsageException exception = Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            personajeService.guardarPersonaje(otroMaguito);
        });
        Assertions.assertEquals("detached entity passed to persist: ar.edu.unq.spring.modelo.Item", exception.getMessage());
    }

    @Test
    public void testSinDetatchedEntityItemException() {
        Item espada = new Item("Espada", 100);
        inventarioService.guardarItem(espada);

        Personaje otroMaguito = new Personaje("Shierke", 10, 70);

        personajeService.guardarPersonaje(otroMaguito);

        otroMaguito.getInventario().add(espada);
        espada.setOwner(otroMaguito);

        personajeService.guardarPersonaje(otroMaguito);
        Personaje maguitoRecuperado = personajeService.recuperarPersonaje(otroMaguito.getId());
        Assertions.assertFalse(maguitoRecuperado.getInventario().isEmpty());
    }

    @Test
    public void testMergeTransientEnCascadaNoFalla() {
        Personaje otroMaguito = new Personaje("Shierke", 10, 70);

        personajeService.guardarPersonaje(otroMaguito);

        Item espada = new Item("Espada", 100);
        otroMaguito.getInventario().add(espada);
        espada.setOwner(otroMaguito);

        personajeService.guardarPersonaje(otroMaguito);
    }

    @Transactional
    @Test
    public void testMergeSincronizaLosCambiosHechosAlObjeto() {
        Item espada = new Item("Espada", 100);
        inventarioService.guardarItem(espada);

        Item mismaEspada = inventarioService.getItem(espada.getId());
        mismaEspada.setNombre("Espada Gastada");
        inventarioService.guardarItem(mismaEspada);

        Personaje otroMaguito = new Personaje("Shierke", 10, 70);

        personajeService.guardarPersonaje(otroMaguito);

        otroMaguito.getInventario().add(espada);
        mismaEspada.setOwner(otroMaguito);

        personajeService.guardarPersonaje(otroMaguito);

        Personaje maguitoRecuperado = personajeService.recuperarPersonaje(otroMaguito.getId());
        Assertions.assertEquals("Espada Gastada", maguitoRecuperado.getInventario().iterator().next().getNombre());
    }

    @AfterEach
    public void tearDown() {
        inventarioService.clearAll();
        personajeService.clearAll();
    }
}