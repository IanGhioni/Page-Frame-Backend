package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.ContenidoDeUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContenidoDeUsuarioDAO extends JpaRepository<ContenidoDeUsuario, Long> {

    @Query("from ContenidoDeUsuario cdu where cdu.usuario.id = :usuarioId and cdu.contenido.id = :contenidoId")
    ContenidoDeUsuario findByUsuarioIdAndContenidoId(Long usuarioId, Long contenidoId);


}
