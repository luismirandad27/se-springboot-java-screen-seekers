/**
 * Class File: MovieRepository.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the Jpa methods to access the Movie table from
 * the database.
 * v1.01: added Find methods and random movies 
 * v1.02: added find available movies (not deleted) 
 * 
 * @authors Victor Chawsukho, Regal Cruz
 * @version 1.02
 * 
 */

package com.webwizards.screenseekers.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webwizards.screenseekers.model.Movie;


public interface MovieRepository extends JpaRepository<Movie, Long>{
	
	List<Movie> findAll();
	
	List<Movie> findByTitle(String title);

	List<Movie> findByTitleContainingIgnoreCase(String title);
	
	List<Movie> findByGenreContainingIgnoreCase(String genre);
	
	@Query("SELECT m FROM Movie m ORDER BY RAND() LIMIT 10")
    List<Movie> findRandom();
	
	@Query("SELECT e FROM Movie e WHERE EXTRACT(YEAR FROM e.releaseDate) = ?1")
	List<Movie> findByReleaseDateYear(int year);
	
	
	//Include Pageable
	Page<Movie> findAll(Pageable page);
	
	Page<Movie> findByTitle(String title, Pageable pageable);

	Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable page);
	
	Page<Movie> findByGenreContainingIgnoreCase(String genre, Pageable page);
	
	@Query("SELECT m FROM Movie m ORDER BY RAND()")
    Page<Movie> findRandom(Pageable pageable);
	
	@Query("SELECT e FROM Movie e WHERE EXTRACT(YEAR FROM e.releaseDate) = ?1")
	Page<Movie> findByReleaseDateYear(int year, Pageable pageable);
	
	Page<Movie> findByIsInTheaters(Boolean isInTheaters, Pageable pageable);
	
	Page<Movie> findByIsInStreaming(Boolean isInStreaming, Pageable pageable);
	
	Page<Movie> findByIsComingSoon(Boolean isComingSoon, Pageable pageable);
	

}