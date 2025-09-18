package ar.edu.unq.spring.servicios;

import ar.edu.unq.spring.jwt.JWTRole;
import ar.edu.unq.spring.modelo.Contenido;
import ar.edu.unq.spring.modelo.Usuario;
import ar.edu.unq.spring.modelo.exception.UsuarioNoEncontrado;
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
                "Fantasy, Adventure", 3000000, 9.4, 377);
        contenidoService.crear(percyJackson);
        usuario = new Usuario("juan123", "juan@gmail.com", "Juan1235678!!", JWTRole.USER);
        usuarioService.crear(usuario);
        madagascar = new Contenido(null, "https://static.wikia.nocookie.net/doblaje/images/0/00/MadagascarPoster.png/revision/latest?cb=20200326204410&path-prefix=es",
                "Madagascar", "Eric Darnell, Tom McGrath", 2005, "Four animal friends get a taste of the wild life when they break out of captivity at the Central Park Zoo and wash ashore on the island of Madagascar.",
                "Animation, Comedy", 465000, 6.9, 86);
        contenidoService.crear(madagascar);
    }

    @Test
    void testCrearContenido() {
        Usuario emily = new Usuario("emily", "emily@gmail.com", "Emily1235678!!", JWTRole.USER);
        Usuario emilyPers = usuarioService.crear(emily);

        assertEquals(emily.getId(), emilyPers.getId());
    }
    @Test
    void testEliminarContenido() {
        Usuario emily = new Usuario("emily2", "emily2@gmial.com", "Emily1235678!!", JWTRole.USER);
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
    void testAgregarOtroContenidoALsita() {
        usuarioService.agregarContenidoAUsuario(usuario.getId(), percyJackson.getId(), "QUIERO_LEER");
        usuarioService.agregarContenidoAUsuario(usuario.getId(), madagascar.getId(), "VISTO");

        var usuarioRecuperado = usuarioService.recuperar(usuario.getId());

        assertEquals(2, usuarioRecuperado.getMisContenidos().size());
        assertEquals("QUIERO_LEER", usuarioRecuperado.getMisContenidos().get(0).getEstado());
        assertEquals("VISTO", usuarioRecuperado.getMisContenidos().get(1).getEstado());
    }

    @AfterEach
    void clean() {
        testService.cleanUp();
    }
}
