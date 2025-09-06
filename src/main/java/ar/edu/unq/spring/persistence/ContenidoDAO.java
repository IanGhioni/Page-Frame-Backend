package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Contenido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenidoDAO extends JpaRepository<Contenido, Long> {

    @Query("from Contenido c order by c.titulo ASC")
    List<Contenido> contenidoOrdPorTituloAsc();

    @Query("from Contenido c order by c.autores ASC")
    List<Contenido> contenidoOrdPorAutoresAsc();

    @Query("from Contenido c where c.titulo ilike %:name% order by c.ratingAverage desc ")
    Page<Contenido> findByTituloContaining(@Param("name") String titulo, Pageable pageable);
}
