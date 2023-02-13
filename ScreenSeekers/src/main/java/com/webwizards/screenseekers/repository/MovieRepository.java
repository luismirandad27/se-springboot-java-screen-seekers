/*
 * Class File: MovieRepository.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the Jpa methods to access the Movie table from
 * the database
 * 
 * @author Regal Cruz
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webwizards.screenseekers.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{
	List<Movie> findByTitle(String title);
}