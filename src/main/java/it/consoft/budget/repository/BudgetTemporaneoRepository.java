package it.consoft.budget.repository;

import it.consoft.budget.domain.BudgetTemporaneo;
import it.consoft.budget.domain.Responsabile;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BudgetTemporaneo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BudgetTemporaneoRepository extends JpaRepository<BudgetTemporaneo,Long> {

	Page<BudgetTemporaneo> findAllByCommessaDivisioneResponsabile(Pageable pageable, Responsabile findOne);

}
