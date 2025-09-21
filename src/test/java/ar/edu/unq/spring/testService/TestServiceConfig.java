package ar.edu.unq.spring.testService;

import ar.edu.unq.spring.persistence.ContenidoDAO;
import ar.edu.unq.spring.persistence.ContenidoDeUsuarioDAO;
import ar.edu.unq.spring.persistence.ReviewDAO;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestServiceConfig {
    @Bean
    public TestService testService(ContenidoDAO contenidoDAO, UsuarioDAO usuarioDAO, ContenidoDeUsuarioDAO contenidoDeUsuarioDAO, ReviewDAO reviewDAO) {
        return new TestServiceImpl(contenidoDAO, usuarioDAO, contenidoDeUsuarioDAO, reviewDAO);

    }
}