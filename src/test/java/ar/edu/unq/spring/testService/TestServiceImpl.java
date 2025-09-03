package ar.edu.unq.spring.testService;

import ar.edu.unq.spring.persistence.ContenidoDAO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TestServiceImpl implements TestService {
    private final ContenidoDAO contenidoDAO;

    public TestServiceImpl(ContenidoDAO contenidoDAO) {
        this.contenidoDAO = contenidoDAO;
    }

    @Override
    public void cleanUp() {
        contenidoDAO.deleteAll();
    }
}
