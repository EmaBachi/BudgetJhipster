package it.consoft.budget.repository;

import it.consoft.budget.domain.Conto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Conto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContoRepository extends JpaRepository<Conto,Long> {

}
