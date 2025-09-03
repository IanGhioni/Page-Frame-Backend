package ar.edu.unq.spring.servicios;

import ar.edu.unq.spring.modelo.Contenido;
import ar.edu.unq.spring.modelo.exception.ContenidoNoEncontradoException;
import ar.edu.unq.spring.testService.TestService;
import ar.edu.unq.spring.testService.TestServiceConfig;
import ar.edu.unq.spring.service.interfaces.ContenidoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestServiceConfig.class)
public class ContenidoServiceTest {

    @Autowired
    private TestService testService;

    @Autowired
    private ContenidoService contenidoService;

    private Contenido percyJackson;
    private Contenido climateBook;

    @BeforeEach
    void prepare() {
        percyJackson = new Contenido("0-7868-5629-7", "https://upload.wikimedia.org/wikipedia/en/3/3b/The_Lightning_Thief_cover.jpg",
                "Percy Jackson & the Olympians: The Lightning Thief", "Rick Riordan", 2005, "A teenager discovers he's the descendant of a Greek god and sets out on an adventure to settle an on-going battle between the gods.",
                "Fantasy, Adventure", 3000000, 9.4, 377);
        contenidoService.crear(percyJackson);

        climateBook = new Contenido("9780593492307", "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1653944198i/61153762.jpg",
                "The Climate Book: The Facts and the Solutions", "Greta Thunberg",2023, "A comprehensive overview of the science of climate change, its impacts, and potential solutions.",
                "Science, Environment, Non-fiction", 3072, 4.40, 464);
        contenidoService.crear(climateBook);
    }

    @Test
    void testCrearContenido() {
        Contenido madagascar = new Contenido(null, "https://static.wikia.nocookie.net/doblaje/images/0/00/MadagascarPoster.png/revision/latest?cb=20200326204410&path-prefix=es",
                "Madagascar", "Eric Darnell, Tom McGrath", 2005, "Four animal friends get a taste of the wild life when they break out of captivity at the Central Park Zoo and wash ashore on the island of Madagascar.",
                "Animation, Comedy", 465000, 6.9, 86);
        var madagascarPers = contenidoService.crear(madagascar);

        assertEquals(madagascar.getTitulo(), madagascarPers.getTitulo());
    }

    @Test
    void testDeleteContenido() {
        Contenido madagascar = new Contenido(null, "https://static.wikia.nocookie.net/doblaje/images/0/00/MadagascarPoster.png/revision/latest?cb=20200326204410&path-prefix=es",
                "Madagascar", "Eric Darnell, Tom McGrath", 2005, "Four animal friends get a taste of the wild life when they break out of captivity at the Central Park Zoo and wash ashore on the island of Madagascar.",
                "Animation, Comedy", 465000, 6.9, 86);
        var madagascarPers = contenidoService.crear(madagascar);

        contenidoService.eliminar(madagascarPers);

        var madagascarElim = contenidoService.recuperar(madagascarPers.getId());
        assertTrue(madagascarElim.isEmpty());
    }

    @Test
    void testDeleteContenidoInexistente() {
        int cantidadInicial = contenidoService.recuperarTodos().size();
        Contenido madagascar = new Contenido(null, "https://static.wikia.nocookie.net/doblaje/images/0/00/MadagascarPoster.png/revision/latest?cb=20200326204410&path-prefix=es",
                "Madagascar", "Eric Darnell, Tom McGrath", 2005, "Four animal friends get a taste of the wild life when they break out of captivity at the Central Park Zoo and wash ashore on the island of Madagascar.",
                "Animation, Comedy", 465000, 6.9, 86);

        assertThrows(ContenidoNoEncontradoException.class, () ->
                contenidoService.eliminar(madagascar));

        int cantidadFinal = contenidoService.recuperarTodos().size();

        assertEquals(cantidadInicial, cantidadFinal);
    }

    @Test
    void testRecuperarContenidoExistente() {
        var percyJacksonPersistido = contenidoService.recuperar(percyJackson.getId());

        assertEquals(percyJackson.getTitulo(), percyJacksonPersistido.get().getTitulo());
    }

    @Test
    void testRecuperarContenidoInexistente() {
        Long id = (long) -1;
        var contenido = contenidoService.recuperar(id);

        assertTrue(contenido.isEmpty());
    }

    @Test
    void testActualizarTituloContenido() {
        percyJackson.setTitulo("PercyJackson y los dioses del Olimpo: El ladrón del rayo");
        contenidoService.actualizar(percyJackson);

        var PJActualizado = contenidoService.recuperar(percyJackson.getId());

        assertEquals(PJActualizado.get().getTitulo(), percyJackson.getTitulo());
    }

    @Test
    void testActualizarContenidoInexistente() {
        Contenido madagascar = new Contenido(null, "https://static.wikia.nocookie.net/doblaje/images/0/00/MadagascarPoster.png/revision/latest?cb=20200326204410&path-prefix=es",
                "Madagascar", "Eric Darnell, Tom McGrath", 2005, "Four animal friends get a taste of the wild life when they break out of captivity at the Central Park Zoo and wash ashore on the island of Madagascar.",
                "Animation, Comedy", 465000, 6.9, 86);

        assertThrows(ContenidoNoEncontradoException.class, () ->
                contenidoService.actualizar(madagascar) );
    }

    @Test
    void testRecuperarTodoElContenido() {
        var todo = contenidoService.recuperarTodos();

        assertEquals(2, todo.size());
    }

    @Test
    void testRecuperarTodoElContenidoOrdenadoPorTituloAsc() {
        var todo = contenidoService.recuperarTodoOrdenadoPorTituloAsc();

        assertEquals(2, todo.size());
        assertEquals(todo.get(0).getTitulo(), percyJackson.getTitulo());
        assertEquals(todo.get(1).getTitulo(), climateBook.getTitulo());
    }

    @Test
    void testRecuperarTodoElContenidoOrdenadoPorAutoresAsc() {
        var todo = contenidoService.recuperarTodoOrdenadoPorAutoresAsc();

        assertEquals(2, todo.size());
        assertEquals(todo.get(0).getAutores(), climateBook.getAutores());
        assertEquals(todo.get(1).getAutores(), percyJackson.getAutores());
    }

    @Test
    void testRecuperarTodoElContenidoConBaseVacia() {
        contenidoService.eliminar(percyJackson);
        contenidoService.eliminar(climateBook);

        var todo = contenidoService.recuperarTodos();

        assertTrue(todo.isEmpty());
    }

    @AfterEach
    void cleanUp() {
        testService.cleanUp();
    }
}