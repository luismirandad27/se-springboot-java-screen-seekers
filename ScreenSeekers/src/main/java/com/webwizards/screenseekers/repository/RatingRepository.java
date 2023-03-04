/**
 * Class File: RatingRepository.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the Jpa methods to access the Rating table from
 * the database
 * 
 * @author Luis Miguel Miranda
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.repository;

import java.util.List;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.webwizards.screenseekers.model.Rating;

@Transactional
public interface RatingRepository extends JpaRepository<Rating,Long>{

	List<Rating> findAllByUserId(Long userId);

	Optional<Rating> findByUserIdAndMovieId(Long userId, Long movieId);
	
	List<Rating> findByUserId(Long userId);
	
	List<Rating> findByMovieId(Long movieId);

	@Query("SELECT r FROM Rating r")
	List<Rating> findAllRatings();
	
	@Modifying
	@Query("DELETE FROM Rating r WHERE r.user.id = ?1")
	void deleteByUserId(Long userId);
	
	@Modifying
	@Query("DELETE FROM Rating r WHERE r.movie.id = ?1")
	void deleteByMovieId(Long movieId);
	
}
