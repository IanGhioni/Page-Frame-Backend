package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Contenido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface ContenidoDAO extends JpaRepository<Contenido, Long> {

    @Query("from Contenido c order by c.titulo ASC")
    List<Contenido> contenidoOrdPorTituloAsc();

    @Query("from Contenido c order by c.autores ASC")
    List<Contenido> contenidoOrdPorAutoresAsc();

    @Query(
            value = """
        select *
        from contenido c
        where c.autores ilike '%' || :name || '%'
        order by 
          (case
            when c.autores ilike :name then 1
            when c.autores ilike :name || '%' then 2
            when c.autores ilike '% ' || :name || ' %' then 3
            when c.autores ilike '%' || :name || '%' then 4
            else 5
          end),
          c.rating_count desc
        """,
            countQuery = """
        select count(*)
        from contenido c
        where c.autores ilike '%' || :name || '%'
        """,
            nativeQuery = true
    )
    Page<Contenido> findByAutores(@Param("name") String nombre, Pageable pageable);

    @Query(
            value = """
        select *
        from contenido c
        where c.titulo ilike '%' || :name || '%'
        order by 
          (case
            when c.titulo ilike :name then 1
            when c.titulo ilike :name || '%' then 2
            when c.titulo ilike '% ' || :name || ' %' then 3
            when c.titulo ilike '%' || :name || '%' then 4
            else 5
          end),
          c.rating_count desc
        """,
            countQuery = """
        select count(*)
        from contenido c
        where c.titulo ilike '%' || :name || '%'
        """,
            nativeQuery = true
    )
    Page<Contenido> findByTituloContainingRelavance(@Param("name") String titulo, Pageable pageable);


    @Query("from Contenido c order by c.ratingCount desc ")
    Page<Contenido> contenidoOrdPorRatingCount(@Param("name") Pageable pageable);


    @Query(
            value = """
        select *
        from contenido c
        where c.titulo ilike '%' || :name || '%' and c.isbn is not null and c.isbn != ''
        order by 
          (case
            when c.titulo ilike :name then 1
            when c.titulo ilike :name || '%' then 2
            when c.titulo ilike '% ' || :name || ' %' then 3
            when c.titulo ilike '%' || :name || '%' then 4
            else 5
          end),
          c.rating_count desc
        """,
            countQuery = """
        select count(*)
        from contenido c
        where c.titulo ilike '%' || :name || '%' and c.isbn is not null and c.isbn != ''
        """,
            nativeQuery = true
    )
    Page<Contenido> findByTituloOnlyBooks(@Param("name") String titulo, Pageable pageable);

    @Query(
            value = """
        select *
        from contenido c
        where c.titulo ilike '%' || :name || '%' and c.isbn = ''
        order by 
          (case
            when c.titulo ilike :name then 1
            when c.titulo ilike :name || '%' then 2
            when c.titulo ilike '% ' || :name || ' %' then 3
            when c.titulo ilike '%' || :name || '%' then 4
            else 5
          end),
          c.rating_count desc
        """,
            countQuery = """
        select count(*)
        from contenido c
        where c.titulo ilike '%' || :name || '%' and c.isbn = ''
        """,
            nativeQuery = true
    )
    Page<Contenido> findByTituloOnlyMovies(@Param("name") String titulo, Pageable pageable);

    @Query(
            value = """
        select *
        from contenido c
        where c.autores ilike '%' || :name || '%' and c.isbn is not null and c.isbn != ''
        order by 
          (case
            when c.autores ilike :name then 1
            when c.autores ilike :name || '%' then 2
            when c.autores ilike '% ' || :name || ' %' then 3
            when c.autores ilike '%' || :name || '%' then 4
            else 5
          end),
          c.rating_count desc
        """,
            countQuery = """
        select count(*)
        from contenido c
        where c.autores ilike '%' || :name || '%' and c.isbn is not null and c.isbn != ''
        """,
            nativeQuery = true
    )
    Page<Contenido> findByAutorOnlyBooks(@Param("name") String titulo, Pageable pageable);

    @Query(
            value = """
        select *
        from contenido c
        where c.autores ilike '%' || :name || '%' and c.isbn = ''
        order by 
          (case
            when c.autores ilike :name then 1
            when c.autores ilike :name || '%' then 2
            when c.autores ilike '% ' || :name || ' %' then 3
            when c.autores ilike '%' || :name || '%' then 4
            else 5
          end),
          c.rating_count desc
        """,
            countQuery = """
        select count(*)
        from contenido c
        where c.autores ilike '%' || :name || '%' and c.isbn = ''
        """,
            nativeQuery = true
    )
    Page<Contenido> findByAutorOnlyMovies(@Param("name") String titulo, Pageable pageable);

    @Query(
            value = """
            select *
            from contenido c
            where c.categoria ilike '%' || :categoria || '%' 
            order by c.rating_count desc
            """,
            countQuery = """
            select count(*)
            from contenido c
            where c.categoria ilike '%' || :categoria || '%' 
            """,
            nativeQuery = true
    )
    Page<Contenido> findByGeneroOrderByRatingCountDesc(@Param("categoria") String categoria, Pageable pageable);

    @Query(
            value = """
            select *
            from contenido c
            where c.categoria ilike '%' || :categoria || '%' 
              and c.isbn is null
            order by c.rating_count desc
            """,
            countQuery = """
            select count(*)
            from contenido c
            where c.categoria ilike '%' || :categoria || '%' 
              and c.isbn is null
            """,
            nativeQuery = true
    )
    Page<Contenido> findByGeneroOnlyMoviesOrderByRatingCountDesc(@Param("categoria") String categoria, Pageable pageable);

    @Query(
            value = """
            select *
            from contenido c
            where c.categoria ilike '%' || :categoria || '%' 
              and c.isbn is not null
            order by c.rating_count desc
            """,
            countQuery = """
            select count(*)
            from contenido c
            where c.categoria ilike '%' || :categoria || '%' 
              and c.isbn is not null
            """,
            nativeQuery = true
    )
    Page<Contenido> findByGeneroOnlyBooksOrderByRatingCountDesc(@Param("categoria") String categoria, Pageable pageable);
}

