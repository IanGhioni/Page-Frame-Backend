package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Contenido;
import ar.edu.unq.spring.modelo.ContenidoDeUsuarioPersonalizado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ContenidoDeUsuarioPersonalizadoDAO extends JpaRepository<ContenidoDeUsuarioPersonalizado, Long> {

    @Query("from ContenidoDeUsuarioPersonalizado cdup where cdup.usuario.id = :usuarioId and cdup.nombre = :nombre")
    ContenidoDeUsuarioPersonalizado findByUsuarioIdAndNombre(Long usuarioId, String nombre);

    @Query("from ContenidoDeUsuarioPersonalizado cdup where cdup.usuario.id = :usuarioId and cdup.nombre = :nombre")
    Set<Contenido> findContenidoDeUsuarioPersonalizadoByUsuarioIdAndNombre(Long usuarioId, String nombre);
}
