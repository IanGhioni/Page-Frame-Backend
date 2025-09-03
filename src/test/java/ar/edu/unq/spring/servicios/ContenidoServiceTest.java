package ar.edu.unq.spring.servicios;

import ar.edu.unq.spring.modelo.Contenido;
import ar.edu.unq.spring.testService.TestServiceConfig;
import ar.edu.unq.spring.service.interfaces.ContenidoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(TestServiceConfig.class)
public class ContenidoServiceTest {

    @Autowired
    private ContenidoService contenidoService;

    @Test
    void testCrearContenido() {
        Contenido madagascar = new Contenido(null, "https://static.wikia.nocookie.net/doblaje/images/0/00/MadagascarPoster.png/revision/latest?cb=20200326204410&path-prefix=es",
                "Madagascar", 2005, "Four animal friends get a taste of the wild life when they break out of captivity at the Central Park Zoo and wash ashore on the island of Madagascar.",
                "Animation, Comedy", 465000, 6.9, 86);
        var madagascarPers = contenidoService.crear(madagascar);

        assertEquals(madagascar.getTitulo(), madagascarPers.getTitulo());
    }
}