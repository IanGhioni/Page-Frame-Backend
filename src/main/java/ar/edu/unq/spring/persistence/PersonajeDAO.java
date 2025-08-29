package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonajeDAO extends JpaRepository<Personaje, Long> {
}