package ar.edu.unq.spring.testService;

import ar.edu.unq.spring.persistence.ContenidoDAO;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TestServiceImpl implements TestService {
    private final ContenidoDAO contenidoDAO;
    private final UsuarioDAO usuarioDAO;

    public TestServiceImpl(ContenidoDAO contenidoDAO, UsuarioDAO usuarioDAO) {
        this.contenidoDAO = contenidoDAO;
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public void cleanUp() {
        contenidoDAO.deleteAll();
        usuarioDAO.deleteAll();
    }
}
