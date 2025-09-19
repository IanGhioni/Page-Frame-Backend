package ar.edu.unq.spring.testService;

import ar.edu.unq.spring.persistence.ContenidoDAO;
import ar.edu.unq.spring.persistence.ContenidoDeUsuarioDAO;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import ar.edu.unq.spring.service.interfaces.ContenidoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TestServiceImpl implements TestService {
    private final ContenidoDAO contenidoDAO;
    private final UsuarioDAO usuarioDAO;
    private final ContenidoDeUsuarioDAO contenidoDeUsuarioDAO;

    public TestServiceImpl(ContenidoDAO contenidoDAO, UsuarioDAO usuarioDAO, ContenidoDeUsuarioDAO contenidoDeUsuarioDAO) {
        this.contenidoDAO = contenidoDAO;
        this.usuarioDAO = usuarioDAO;
        this.contenidoDeUsuarioDAO = contenidoDeUsuarioDAO;
    }

    @Override
    public void cleanUp() {
        contenidoDeUsuarioDAO.deleteAll();
        usuarioDAO.deleteAll();
        contenidoDAO.deleteAll();
    }
}
