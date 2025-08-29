package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ItemDAO extends JpaRepository<Item, Long> {

    Item findTopByOrderByPesoDesc();

    @Query(
            "FROM Item i where i.peso  > :peso order by i.peso asc"
    )
    Set<Item> getMasPesados(@Param("peso") int peso);

    @Query(
            "from Item i "
                    + "where i.owner.vida < ?1 "
                    + "order by i.peso asc"
    )
    Set<Item> getItemsDePersonajesDebiles(int vida);
}