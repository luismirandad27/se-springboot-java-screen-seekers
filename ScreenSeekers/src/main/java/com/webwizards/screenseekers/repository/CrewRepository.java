/**
 * Class File: MovieRepository.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the Jpa methods to access the Crew table from
 * the database.
 *
 * 
 	Regal Cruz
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webwizards.screenseekers.model.Crew;

public interface CrewRepository extends JpaRepository<Crew, Long> {
	List<Crew> findByFirstName(String firstName);
}
