package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenidoDAO extends JpaRepository<Contenido, Long> {

    @Query("from Contenido c order by c.titulo ASC")
    List<Contenido> contenidoOrdPorTituloAsc();

    @Query("from Contenido c order by c.autores ASC")
    List<Contenido> contenidoOrdPorAutoresAsc();
}
