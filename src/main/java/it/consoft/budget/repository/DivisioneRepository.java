package it.consoft.budget.repository;

import it.consoft.budget.domain.Divisione;
import it.consoft.budget.domain.Responsabile;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Divisione entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DivisioneRepository extends JpaRepository<Divisione,Long> {

	Page<Divisione> findAllByResponsabile(Pageable pageable, Responsabile responsabile);

}
