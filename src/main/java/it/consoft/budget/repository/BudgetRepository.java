package it.consoft.budget.repository;

import it.consoft.budget.domain.Budget;
import it.consoft.budget.domain.Responsabile;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Budget entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BudgetRepository extends JpaRepository<Budget,Long> {

	Page<Budget> findAllByCommessaDivisioneResponsabile(Pageable pageable, Responsabile responsabile);

}
