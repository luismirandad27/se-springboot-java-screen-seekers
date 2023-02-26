/*
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

import com.webwizards.screenseekers.model.Rating;

public interface RatingRepository extends JpaRepository<Rating,Long>{

	List<Rating> findAllByUserId(Long userId);
	Optional<Rating> findByUserId(Long userId);
	
	
}
