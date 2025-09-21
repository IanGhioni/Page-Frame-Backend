package ar.edu.unq.spring.testService;

import ar.edu.unq.spring.persistence.ContenidoDAO;
import ar.edu.unq.spring.persistence.ContenidoDeUsuarioDAO;
import ar.edu.unq.spring.persistence.ReviewDAO;
import ar.edu.unq.spring.persistence.UsuarioDAO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TestServiceImpl implements TestService {
    private final ContenidoDAO contenidoDAO;
    private final UsuarioDAO usuarioDAO;
    private final ContenidoDeUsuarioDAO contenidoDeUsuarioDAO;
    private final ReviewDAO reviewDAO;

    public TestServiceImpl(ContenidoDAO contenidoDAO, UsuarioDAO usuarioDAO, ContenidoDeUsuarioDAO contenidoDeUsuarioDAO, ReviewDAO reviewDAO) {
        this.contenidoDAO = contenidoDAO;
        this.usuarioDAO = usuarioDAO;
        this.contenidoDeUsuarioDAO = contenidoDeUsuarioDAO;
        this.reviewDAO = reviewDAO;
    }

    @Override
    public void cleanUp() {
        contenidoDeUsuarioDAO.deleteAll();
        reviewDAO.deleteAll();
        usuarioDAO.deleteAll();
        contenidoDAO.deleteAll();
    }
}
