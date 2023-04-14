/**
 * Class File: ProductionCrewRepository.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the Jpa methods to access the ProductionCrew table from
 * the database.
 *
 * 
 * @Author Regal Cruz, Luis Miguel Miranda
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.webwizards.screenseekers.model.ProductionCrew;

@Transactional
public interface ProductionCrewRepository extends JpaRepository<ProductionCrew, Long> {

	List<ProductionCrew> findByMovieId(Long movieId);
	
	@Modifying
	@Query("DELETE FROM ProductionCrew p WHERE p.movie.id = ?1 AND p.crewMember.id = ?2")
	void deleteByMovieIdAndCrewMemberId(Long movieId, Long crewMemberId);
	
	Optional<ProductionCrew> findByMovieIdAndCrewMemberId(Long movieId, Long crewMemberId);
	
}
