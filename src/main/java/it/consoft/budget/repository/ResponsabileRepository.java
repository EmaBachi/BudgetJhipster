package it.consoft.budget.repository;

import it.consoft.budget.domain.Responsabile;
import it.consoft.budget.domain.User;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Responsabile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponsabileRepository extends JpaRepository<Responsabile,Long> {

	Page<Responsabile> findAllByUser(Pageable pageable, User user);

	Responsabile findOneByUser(User user);

}
