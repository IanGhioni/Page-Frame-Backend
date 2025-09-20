package ar.edu.unq.spring.persistence;

import ar.edu.unq.spring.modelo.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewDAO extends JpaRepository<Review, Long> {
}
