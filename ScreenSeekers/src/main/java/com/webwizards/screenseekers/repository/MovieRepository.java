/*
 * Class File: MovieRepository.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the Jpa methods to access the Movie table from
 * the database.
 * v1.01: added Find methods and random movies 
 * 
 * @authors Victor Chawsukho, Regal Cruz
 * @version 1.01
 * 
 */

package com.webwizards.screenseekers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.webwizards.screenseekers.model.Movie;

public interface MovieRepository extends JpaRepository <Movie, Long>{
	List<Movie> findByTitleContainingIgnoreCase(String title);
	List<Movie> findByGenreContainingIgnoreCase(String genre);
	
	@Query("SELECT m FROM Movie m ORDER BY RAND() LIMIT 10")
    List<Movie> findRandom();
	
	
	@Query("SELECT e FROM Movie e WHERE EXTRACT(YEAR FROM e.releaseDate) = ?1")
	List<Movie> findByReleaseDateYear(int year);
	
}
