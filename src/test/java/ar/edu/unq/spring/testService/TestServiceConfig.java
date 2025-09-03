package ar.edu.unq.spring.testService;

import ar.edu.unq.spring.persistence.ContenidoDAO;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestServiceConfig {
    @Bean
    public TestService testService(ContenidoDAO contenidoDAO) {
        return new TestServiceImpl(contenidoDAO);
    }
}