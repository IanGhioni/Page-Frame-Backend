package ar.edu.unq.spring.servicios;

import ar.edu.unq.spring.jwt.JWTRole;
import ar.edu.unq.spring.modelo.Contenido;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.exception.ContenidoNoEncontradoException;
import ar.edu.unq.spring.exception.NroDePaginaInvalidoException;
import ar.edu.unq.spring.exception.TamanioDePaginaInvalidoException;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import ar.edu.unq.spring.testService.TestService;
import ar.edu.unq.spring.testService.TestServiceConfig;
import ar.edu.unq.spring.service.interfaces.ContenidoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestServiceConfig.class)
public class ContenidoServiceTest {

    @Autowired
    private TestService testService;

    @Autowired
    private ContenidoService contenidoService;

    @Autowired
    private UsuarioService usuarioService;

    private Contenido percyJackson;
    private Contenido climateBook;
    private Contenido velocipastor;
    private Usuario usuario;

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

        usuario = new Usuario("juan123", "juan@gmail.com", "Juan1235678!!", JWTRole.USER, "panda");
        usuarioService.crear(usuario);
        velocipastor = new Contenido(null, "https://m.media-amazon.com/images/I/51e15zlvVDL._AC_SY200_QL15_.jpg",
                "Velocipastor", "Brendan Steere", 2018, "Un sacerdote obtiene la habilidad de convertirse en dinosaurio y la usa para luchar contra los ninjas traficantes de drogas.",
                "Comedy", 5, 2.0, 69);
        contenidoService.crear(velocipastor);
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

        assertEquals(3, todo.size());
    }

    @Test
    void testRecuperarTodoElContenidoOrdenadoPorTituloAsc() {
        var todo = contenidoService.recuperarTodoOrdenadoPorTituloAsc();

        assertEquals(3, todo.size());
        assertEquals(todo.get(0).getTitulo(), percyJackson.getTitulo());
        assertEquals(todo.get(1).getTitulo(), climateBook.getTitulo());
        assertEquals(todo.get(2).getTitulo(), velocipastor.getTitulo());
    }

    @Test
    void testRecuperarTodoElContenidoOrdenadoPorAutoresAsc() {
        var todo = contenidoService.recuperarTodoOrdenadoPorAutoresAsc();

        assertEquals(3, todo.size());
        assertEquals(todo.get(0).getAutores(), velocipastor.getAutores());
        assertEquals(todo.get(1).getAutores(), climateBook.getAutores());
        assertEquals(todo.get(2).getAutores(), percyJackson.getAutores());
    }

    @Test
    void testRecuperarTodoElContenidoConBaseVacia() {
        contenidoService.eliminar(percyJackson);
        contenidoService.eliminar(climateBook);
        contenidoService.eliminar(velocipastor);

        var todo = contenidoService.recuperarTodos();

        assertTrue(todo.isEmpty());
    }

    @Test
    void testRecuperarPaginado() {
        poblarBaseParaBusquedaPorNombre();

        Page<Contenido> p = contenidoService.recuperarPorNombre("War",0,2);

        assertEquals(2, p.get().toList().size());
        assertTrue(p.get().toList().get(0).getRatingCount()>p.get().toList().get(1).getRatingCount());
    }

    @Test
    void testRecuperarPaginadoNumeroDePaginaInvalido() {
        poblarBaseParaBusquedaPorNombre();

        assertThrows(NroDePaginaInvalidoException.class, () ->
                contenidoService.recuperarPorNombre("War",-1,2)
        );
    }

    @Test
    void testRecuperarPaginadoTamanioDePaginaInvalido() {
        poblarBaseParaBusquedaPorNombre();

        assertThrows(TamanioDePaginaInvalidoException.class, () ->
                contenidoService.explorarContenidoPopular(1,0)
        );
    }

    @Test
    void testExplorarPaginado() {
        poblarBaseParaBusquedaPorNombre();

        Page<Contenido> p = contenidoService.explorarContenidoPopular(0,2);

        assertEquals(2, p.get().toList().size());
        assertTrue(p.get().toList().get(0).getRatingCount()>p.get().toList().get(1).getRatingCount());
    }

    @Test
    void testExplorarPaginadoNumeroDePaginaInvalido() {
        poblarBaseParaBusquedaPorNombre();

        assertThrows(NroDePaginaInvalidoException.class, () ->
                contenidoService.explorarContenidoPopular(-1,2)
        );
    }

    @Test
    void testExplorarPaginadoTamanioDePaginaInvalido() {
        poblarBaseParaBusquedaPorNombre();

        assertThrows(TamanioDePaginaInvalidoException.class, () ->
                contenidoService.explorarContenidoPopular(1,0)
        );
    }

    void poblarBaseParaBusquedaPorNombre() {
        contenidoService.crear(new Contenido("9789963485734", "https://www.gutenberg.org/files/36/36-h/images/cover.jpg",
                "War Of The Worlds", "H.G. Wells", 1898 , "The War of the Worlds is one of the most frightening science fiction novels ever written. When a spaceship falls from the sky and lands in southern England, few people are worried. But when strange creatures climb out and start killing, nobody is safe.",
                "Science Fiction, War", 3100000, 9.4, 377)
        );
        contenidoService.crear(new Contenido("451169514", "http://images.amazon.com/images/P/0451169514.01.LZZZZZZZ.jpg",
                "IT", "Stephen King", 1997 , """
                It: Chapter Two—now a major motion picture!
                \s
                 Stephen King’s terrifying, classic #1 New York Times bestseller, “a landmark in American literature” (Chicago Sun-Times)—about seven adults who return to their hometown to confront a nightmare they had first stumbled on as teenagers…an evil without a name: It.

                Welcome to Derry, Maine. It’s a small city, a place as hauntingly familiar as your own hometown. Only in Derry the haunting is real.
                \s
                They were seven teenagers when they first stumbled upon the horror. Now they are grown-up men and women who have gone out into the big world to gain success and happiness. But the promise they made twenty-eight years ago calls them reunite in the same place where, as teenagers, they battled an evil creature that preyed on the city’s children. Now, children are being murdered again and their repressed memories of that terrifying summer return as they prepare to once again battle the monster lurking in Derry’s sewers.
                \s
                Readers of Stephen King know that Derry, Maine, is a place with a deep, dark hold on the author. It reappears in many of his books, including Bag of Bones, Hearts in Atlantis, and 11/22/63. But it all starts with It.
                \s
                “Stephen King’s most mature work” (St. Petersburg Times), “It will overwhelm you…to be read in a well-lit room only” (Los Angeles Times).""",
                "Fiction, Horror", 426, 4.5, 1168)
        );
        contenidoService.crear(new Contenido("9789963485732", "https://www.gutenberg.org/files/36/36-h/images/cover.jpg",
                "War against the universe", "H.G. Wells", 1898 , "The War of the Worlds is one of the most frightening science fiction novels ever written. When a spaceship falls from the sky and lands in southern England, few people are worried. But when strange creatures climb out and start killing, nobody is safe.",
                "Science Fiction, War", 3200000, 9.4, 377)
        );
        contenidoService.crear(new Contenido("9789963485733", "https://www.gutenberg.org/files/36/36-h/images/cover.jpg",
                "This is WAR", "H.G. Wells", 1898 , "The War of the Worlds is one of the most frightening science fiction novels ever written. When a spaceship falls from the sky and lands in southern England, few people are worried. But when strange creatures climb out and start killing, nobody is safe.",
                "Science Fiction, War", 3300000, 9.4, 377)
        );
    }

    @Test
    void testValorarContenido() {
        contenidoService.valorarContenido(velocipastor.getId(), 5.0, usuario.getId());

        var contenidoRecuperado = contenidoService.recuperar(velocipastor.getId()).get();

        assertEquals(1, contenidoRecuperado.getReviews().size());
        assertEquals(5.0, contenidoRecuperado.getReviews().get(0).getValoracion());
        assertEquals(6, contenidoRecuperado.getRatingCount());
        assertEquals(2.5, contenidoRecuperado.getRatingAverage());
    }

    @Test
    void testActualizarValoracion() {
        contenidoService.valorarContenido(velocipastor.getId(), 5.0, usuario.getId());
        contenidoService.valorarContenido(velocipastor.getId(), 4.0, usuario.getId());
        var contenidoRecuperado = contenidoService.recuperar(velocipastor.getId()).get();

        assertEquals(1, contenidoRecuperado.getReviews().size());
        assertEquals(4.0, contenidoRecuperado.getReviews().get(0).getValoracion());
        assertEquals(6, contenidoRecuperado.getRatingCount());
        assertEquals(2.75, contenidoRecuperado.getRatingAverage());
    }

    @Test
    void testEliminarValoracion() {
        contenidoService.valorarContenido(velocipastor.getId(), 5.0, usuario.getId());
        contenidoService.eliminarValoracionContenido(velocipastor.getId(), usuario.getId());
        var contenidoRecuperado = contenidoService.recuperar(velocipastor.getId()).get();
        assertEquals(0, contenidoRecuperado.getReviews().size());
    }

    @Test
    void testBuscarPorNombreDeAutoresConUnSoloContenido() {
        Contenido madagascar = new Contenido(null, "https://static.wikia.nocookie.net/doblaje/images/0/00/MadagascarPoster.png/revision/latest?cb=20200326204410&path-prefix=es",
                "Madagascar", "Eric Darnell, Tom McGrath", 2005, "Four animal friends get a taste of the wild life when they break out of captivity at the Central Park Zoo and wash ashore on the island of Madagascar.",
                "Animation, Comedy", 465000, 6.9, 86);
        contenidoService.crear(madagascar);

        var pagina =  contenidoService.recuperarPorNombreDeAutores("Eric Darnell", 0, 1);
        Assertions.assertEquals(1, pagina.getTotalPages());
        Assertions.assertEquals(1, pagina.get().toList().size());
        Assertions.assertEquals("Madagascar", pagina.stream().toList().getFirst().getTitulo());
        Assertions.assertEquals("Eric Darnell, Tom McGrath", pagina.stream().toList().getFirst().getAutores());
    }

    @Test
    void testBuscarPorNombreDeAutoresConVariosContenidos() {
        Contenido madagascar = new Contenido(null, "https://static.wikia.nocookie.net/doblaje/images/0/00/MadagascarPoster.png/revision/latest?cb=20200326204410&path-prefix=es",
                "Madagascar", "Eric Darnell, Tom McGrath", 2005, "Four animal friends get a taste of the wild life when they break out of captivity at the Central Park Zoo and wash ashore on the island of Madagascar.",
                "Animation, Comedy", 465000, 6.9, 86);
        Contenido madagascar2 = new Contenido(null, "https://static.wikia.nocookie.net/doblaje/images/0/00/MadagascarPoster.png/revision/latest?cb=20200326204410&path-prefix=es",
                "Madagascar 2", "Eric Darnell", 2008, "Four animal friends get a taste of the wild life when they break out of captivity at the Central Park Zoo and wash ashore on the island of Madagascar.",
                "Animation, Comedy", 300000, 6.9, 86);
        contenidoService.crear(madagascar);
        contenidoService.crear(madagascar2);

        var pagina =  contenidoService.recuperarPorNombreDeAutores("Eric Darnell", 0, 2);
        Assertions.assertEquals(1, pagina.getTotalPages());
        Assertions.assertEquals(2, pagina.get().toList().size());
        Assertions.assertEquals("Madagascar 2", pagina.stream().toList().getFirst().getTitulo());
        Assertions.assertEquals("Eric Darnell", pagina.stream().toList().getFirst().getAutores());

        Assertions.assertEquals("Madagascar", pagina.stream().toList().getLast().getTitulo());
        Assertions.assertEquals("Eric Darnell, Tom McGrath", pagina.stream().toList().getLast().getAutores());
    }

    @Test
    void testBuscarPorNombreDeAutoresArrojaErrorAlBuscarConPaginaMenorA0() {
        assertThrows(NroDePaginaInvalidoException.class, () -> {
            contenidoService.recuperarPorNombreDeAutores("Saraza", -1, 5);
        });
    }

    @Test
    void testBuscarPorNombreDeAutoresArrojaErrorAlBuscarConTamanioPaginaMenorA1() {
        assertThrows(TamanioDePaginaInvalidoException.class, () -> {
            contenidoService.recuperarPorNombreDeAutores("Saraza", 1, 0);
        });
        assertThrows(TamanioDePaginaInvalidoException.class, () -> {
            contenidoService.recuperarPorNombreDeAutores("Saraza", 1, -1);
        });
    }

    @Test
    void testBuscarPorNombreDeAutoresConVariosContenidosYVariasPaginas() {
        Contenido madagascar = new Contenido(null, "https://static.wikia.nocookie.net/doblaje/images/0/00/MadagascarPoster.png/revision/latest?cb=20200326204410&path-prefix=es",
                "Madagascar", "Eric Darnell, Tom McGrath", 2005, "Four animal friends get a taste of the wild life when they break out of captivity at the Central Park Zoo and wash ashore on the island of Madagascar.",
                "Animation, Comedy", 465000, 6.9, 86);
        Contenido madagascar2 = new Contenido(null, "https://static.wikia.nocookie.net/doblaje/images/0/00/MadagascarPoster.png/revision/latest?cb=20200326204410&path-prefix=es",
                "Madagascar 2", "Eric Darnell", 2008, "Four animal friends get a taste of the wild life when they break out of captivity at the Central Park Zoo and wash ashore on the island of Madagascar.",
                "Animation, Comedy", 300000, 6.9, 86);
        contenidoService.crear(madagascar);
        contenidoService.crear(madagascar2);

        var pagina =  contenidoService.recuperarPorNombreDeAutores("r", 0, 2);
        var pagina2 =  contenidoService.recuperarPorNombreDeAutores("r", 1, 2);
        var pagina3 =  contenidoService.recuperarPorNombreDeAutores("r", 2, 2);

        var contenidoPaginaUno  = pagina.get().toList();
        var contenidoPaginaDos  = pagina2.get().toList();
        var contenidoPaginaTres = pagina3.get().toList();


        Assertions.assertEquals(3, pagina.getTotalPages());
        Assertions.assertEquals(5, pagina.getTotalElements());

        Assertions.assertEquals(2, contenidoPaginaUno.size());
        Assertions.assertEquals(2, contenidoPaginaDos.size());
        Assertions.assertEquals(1, contenidoPaginaTres.size());
    }

    @Test
    void buscarPorCreadorSinCoincidenciasRetornaVacio() {
        var pagina =  contenidoService.recuperarPorNombreDeAutores("RRRRRRR", 0, 10);

        Assertions.assertEquals(0, pagina.getTotalPages());
        Assertions.assertEquals(0, pagina.getTotalElements());
        Assertions.assertEquals(0, pagina.get().toList().size());
    }


    @AfterEach
    void cleanUp() {
        testService.cleanUp();
    }
}