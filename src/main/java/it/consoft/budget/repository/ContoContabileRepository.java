package it.consoft.budget.repository;

import it.consoft.budget.domain.ContoContabile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContoContabile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContoContabileRepository extends JpaRepository<ContoContabile,Long> {

}
