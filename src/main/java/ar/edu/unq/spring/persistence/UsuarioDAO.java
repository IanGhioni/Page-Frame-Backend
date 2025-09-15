package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
