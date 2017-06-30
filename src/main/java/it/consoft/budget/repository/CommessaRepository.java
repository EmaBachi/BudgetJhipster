package it.consoft.budget.repository;

import it.consoft.budget.domain.Commessa;
import it.consoft.budget.domain.Responsabile;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Commessa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommessaRepository extends JpaRepository<Commessa,Long> {

	Page<Commessa> findAllByDivisioneResponsabile(Pageable pageable, Responsabile responsabile);

}
