package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContenidoDAO extends JpaRepository<Contenido, Long> {
}
