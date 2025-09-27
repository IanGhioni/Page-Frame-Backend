package ar.edu.unq.spring.servicios;

import ar.edu.unq.spring.exception.ListaExistenteException;
import ar.edu.unq.spring.jwt.JWTRole;
import ar.edu.unq.spring.modelo.Contenido;
import ar.edu.unq.spring.modelo.ContenidoDeUsuarioPersonalizado;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.exception.UsuarioNoEncontrado;
import ar.edu.unq.spring.service.interfaces.ContenidoService;
import ar.edu.unq.spring.service.interfaces.UsuarioService;
import ar.edu.unq.spring.testService.TestService;
import ar.edu.unq.spring.testService.TestServiceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestServiceConfig.class)
public class UsuarioServiceTest {
    @Autowired
    private TestService testService;

    @Autowired
    private ContenidoService contenidoService;

    @Autowired
    private UsuarioService usuarioService;

    private Contenido percyJackson;
    private Contenido madagascar;
    private Usuario usuario;

    @BeforeEach
    void prepare() {
        percyJackson = new Contenido("0-7868-5629-7", "https://upload.wikimedia.org/wikipedia/en/3/3b/The_Lightning_Thief_cover.jpg",
                "Percy Jackson & the Olympians: The Lightning Thief", "Rick Riordan", 2005, "A teenager discovers he's the descendant of a Greek god and sets out on an adventure to settle an on-going battle between the gods.",
                "Fantasy, Adventure", 3000000, 3.4, 377);
        contenidoService.crear(percyJackson);
        usuario = new Usuario("juan123", "juan@gmail.com", "Juan1235678!!", JWTRole.USER, "panda");
        usuarioService.crear(usuario);
        madagascar = new Contenido(null, "https://static.wikia.nocookie.net/doblaje/images/0/00/MadagascarPoster.png/revision/latest?cb=20200326204410&path-prefix=es",
                "Madagascar", "Eric Darnell, Tom McGrath", 2005, "Four animal friends get a taste of the wild life when they break out of captivity at the Central Park Zoo and wash ashore on the island of Madagascar.",
                "Animation, Comedy", 465000, 2.9, 86);
        contenidoService.crear(madagascar);
    }

    @Test
    void testCrearContenido() {
        Usuario emily = new Usuario("emily", "emily@gmail.com", "Emily1235678!!", JWTRole.USER, "panda");
        Usuario emilyPers = usuarioService.crear(emily);

        assertEquals(emily.getId(), emilyPers.getId());
    }

    @Test
    void testEliminarContenido() {
        Usuario emily = new Usuario("emily2", "emily2@gmial.com", "Emily1235678!!", JWTRole.USER, "panda");
        var emilyPers = usuarioService.crear(emily);
        usuarioService.eliminar(emilyPers);

        assertThrows(UsuarioNoEncontrado.class, () -> usuarioService.recuperar(emilyPers.getId()));
    }

    @Test
    void testAgregarContenidoAUsuario() {
        usuarioService.agregarContenidoAUsuario(usuario.getId(), percyJackson.getId(), "QUIERO_LEER");

        var usuarioRecuperado = usuarioService.recuperar(usuario.getId());

        assertEquals(1, usuarioRecuperado.getMisContenidos().size());
        assertEquals("QUIERO_LEER", usuarioRecuperado.getMisContenidos().get(0).getEstado());
    }

    @Test
    void testAgregarContenidoAOtraLista() {
        usuarioService.agregarContenidoAUsuario(usuario.getId(), percyJackson.getId(), "QUIERO_LEER");
        usuarioService.agregarContenidoAUsuario(usuario.getId(), percyJackson.getId(), "LEIDO");

        var usuarioRecuperado = usuarioService.recuperar(usuario.getId());

        assertEquals(1, usuarioRecuperado.getMisContenidos().size());
        assertEquals("LEIDO", usuarioRecuperado.getMisContenidos().get(0).getEstado());
    }

    @Test
    void testAgregarOtroContenidoALista() {
        usuarioService.agregarContenidoAUsuario(usuario.getId(), percyJackson.getId(), "QUIERO_LEER");
        usuarioService.agregarContenidoAUsuario(usuario.getId(), madagascar.getId(), "VISTO");

        var usuarioRecuperado = usuarioService.recuperar(usuario.getId());

        assertEquals(2, usuarioRecuperado.getMisContenidos().size());
        assertEquals("QUIERO_LEER", usuarioRecuperado.getMisContenidos().get(0).getEstado());
        assertEquals("VISTO", usuarioRecuperado.getMisContenidos().get(1).getEstado());
    }

    @Test
    void testEliminarContenidoDeLista() {
        usuarioService.agregarContenidoAUsuario(usuario.getId(), percyJackson.getId(), "QUIERO_LEER");
        var usuarioRecuperado = usuarioService.recuperar(usuario.getId());
        var idContenido = usuarioRecuperado.getMisContenidos().get(0).getContenido().getId();
        usuarioService.eliminarContenidoDeUsuario(usuario.getId(), idContenido);
        usuarioRecuperado = usuarioService.recuperar(usuario.getId());
        assertEquals(0, usuarioRecuperado.getMisContenidos().size());
    }

    @Test
    void testRecuperarListaLeidos() {
        usuarioService.agregarContenidoAUsuario(usuario.getId(), percyJackson.getId(), "LEIDO");
        usuarioService.agregarContenidoAUsuario(usuario.getId(), madagascar.getId(), "VISTO");
        var leidos = usuarioService.getContenidosDeUsuarioConEstado(usuario.getId(), "LEIDO");
        assertEquals(1, leidos.size());
        assertEquals("Percy Jackson & the Olympians: The Lightning Thief", leidos.get(0).getContenido().getTitulo());
    }

    @Test
    void testCrearListaPersonalizada() {
        Usuario orne = new Usuario("orne", "orne@gmail.com", "Orne1235678!!", JWTRole.USER, "panda");
        Usuario ornePers = usuarioService.crear(orne);

        usuarioService.crearListaPersonalizada(ornePers.getId(), "contenidoFav", "libros y pelis que cambiaron mi vida");

        Usuario ornePersConLista = usuarioService.recuperar(ornePers.getId());
        assertEquals(1, ornePersConLista.getListasPersonalizadas().size());
    }

    @Test
    void testAgregarContenidoAListaPersonalizada() {
        Usuario usuario = usuarioService.crear(new Usuario("orne1", "orne1@gmail.com", "Orne1235678!!", JWTRole.USER, "panda"));

        usuarioService.crearListaPersonalizada(usuario.getId(), "favoritos", "lista de pelis y libros");

        usuarioService.agregarContenidoAListaPersonalizada(usuario.getId(), madagascar.getId(), "favoritos");

        Usuario actualizado = usuarioService.recuperar(usuario.getId());
        Set<Contenido> lista = usuarioService.getContenidosDeListaPersonalizada(actualizado.getId(), "favoritos");

        assertEquals(1, lista.size());
        assertTrue(lista.stream().anyMatch(c -> c.getId().equals(madagascar.getId())));
    }

    @Test
    void testGetListasPersonalizadasDeUsuario() {
        Usuario usuario = usuarioService.crear(new Usuario("orne1", "orne1@gmail.com", "Orne1235678!!", JWTRole.USER, "panda"));

        usuarioService.crearListaPersonalizada(usuario.getId(), "favoritos", "lista de pelis y libros");

        usuarioService.agregarContenidoAListaPersonalizada(usuario.getId(), madagascar.getId(), "favoritos");

        var listas = usuarioService.getListasPersonalizadasDeUsuario(usuario.getId());

        assertEquals(1, listas.size());
    }

    @Test
    void testEliminarListaPersonalizada() {
        Usuario usuario = usuarioService.crear(new Usuario("pepe", "pepe@gmail.com", "Pepe1235678!!", JWTRole.USER, "panda"));

        usuarioService.crearListaPersonalizada(usuario.getId(), "favoritos", "lista de pelis y libros");

        usuarioService.eliminarListaPersonalizada(usuario.getId(), "favoritos");

        var listas = usuarioService.getListasPersonalizadasDeUsuario(usuario.getId());

        assertEquals(0, listas.size());
    }

    @Test
    void testEliminarListaPersonalizada2() {
        Usuario usuario = usuarioService.crear(new Usuario("pepo", "pepo@gmail.com", "Pepo1235678!!", JWTRole.USER, "panda"));

        usuarioService.crearListaPersonalizada(usuario.getId(), "favoritos", "lista de pelis y libros");
        usuarioService.crearListaPersonalizada(usuario.getId(), "pelis", "pelis que me gustan");

        usuarioService.eliminarListaPersonalizada(usuario.getId(), "favoritos");

        var listas = usuarioService.getListasPersonalizadasDeUsuario(usuario.getId());

        assertEquals(1, listas.size());
    }

    @Test
    void testEliminarContenidoDeListaPersonalizada() {
        Usuario usuario = usuarioService.crear(new Usuario("orne2", "orne2@gmail.com", "Orne1235678!!", JWTRole.USER, "panda"));

        usuarioService.crearListaPersonalizada(usuario.getId(), "favoritos", "lista de pelis y libros");

        usuarioService.agregarContenidoAListaPersonalizada(usuario.getId(), madagascar.getId(), "favoritos");

        usuarioService.eliminarContenidoDeListaPersonalizada(usuario.getId(), madagascar.getId(), "favoritos");

        Usuario actualizado = usuarioService.recuperar(usuario.getId());
        Set<Contenido> lista = usuarioService.getContenidosDeListaPersonalizada(actualizado.getId(), "favoritos");

        assertTrue(lista.isEmpty());
    }

    @Test
    void testEliminarContenidoDeListaPersonalizada2() {
        Usuario usuario = usuarioService.crear(new Usuario("orne2", "orne2@gmail.com", "Orne1235678!!", JWTRole.USER, "panda"));

        usuarioService.crearListaPersonalizada(usuario.getId(), "favoritos", "lista de pelis y libros");

        usuarioService.agregarContenidoAListaPersonalizada(usuario.getId(), madagascar.getId(), "favoritos");
        usuarioService.agregarContenidoAListaPersonalizada(usuario.getId(), percyJackson.getId(), "favoritos");

        usuarioService.eliminarContenidoDeListaPersonalizada(usuario.getId(), madagascar.getId(), "favoritos");

        Usuario actualizado = usuarioService.recuperar(usuario.getId());
        Set<Contenido> lista = usuarioService.getContenidosDeListaPersonalizada(actualizado.getId(), "favoritos");

        assertEquals(1, lista.size());
    }

    @Test
    void testAgregarMismoContenidoAListaPersonalizadaLoAgregaUnaVez() {
        Usuario usuario = usuarioService.crear(new Usuario("orne1", "orne1@gmail.com", "Orne1235678!!", JWTRole.USER, "panda"));

        usuarioService.crearListaPersonalizada(usuario.getId(), "favoritos", "lista de pelis y libros");

        usuarioService.agregarContenidoAListaPersonalizada(usuario.getId(), madagascar.getId(), "favoritos");
        usuarioService.agregarContenidoAListaPersonalizada(usuario.getId(), madagascar.getId(), "favoritos");

        Usuario actualizado = usuarioService.recuperar(usuario.getId());
        Set<Contenido> lista = usuarioService.getContenidosDeListaPersonalizada(actualizado.getId(), "favoritos");

        assertEquals(1, lista.size());
    }

    @Test
    void testNoSePuedeCrearListasPersonalizadasConMismoNombre() {
        Usuario usuario = usuarioService.crear(new Usuario("orne1", "orne1@gmail.com", "Orne1235678!!", JWTRole.USER, "panda"));

        usuarioService.crearListaPersonalizada(usuario.getId(), "favoritos", "lista de pelis y libros");
        assertThrows(ListaExistenteException.class, () -> usuarioService.crearListaPersonalizada(usuario.getId(), "favoritos", "lista de pelis y libros"));
    }

    @Test
    void testSePuedeAgregarUnMismoContenidoA2ListasPersonalizadas() {
        Usuario orne = new Usuario("orne", "orne@gmail.com", "Orne1235678!!", JWTRole.USER, "panda");
        Usuario ornePers = usuarioService.crear(orne);

        usuarioService.crearListaPersonalizada(ornePers.getId(), "contenidoFav", "libros y pelis que cambiaron mi vida");
        usuarioService.crearListaPersonalizada(ornePers.getId(), "favoritos", "lista de pelis y libros");

        usuarioService.agregarContenidoAListaPersonalizada(ornePers.getId(), madagascar.getId(), "contenidoFav");
        usuarioService.agregarContenidoAListaPersonalizada(ornePers.getId(), madagascar.getId(), "favoritos");

        Set<Contenido> lista1 = usuarioService.getContenidosDeListaPersonalizada(ornePers.getId(), "contenidoFav");
        Set<Contenido> lista2 = usuarioService.getContenidosDeListaPersonalizada(ornePers.getId(), "favoritos");

        assertEquals(1, lista1.size());
        assertEquals(1, lista2.size());
    }

    @AfterEach
        void clean () {
            testService.cleanUp();
        }
    }

