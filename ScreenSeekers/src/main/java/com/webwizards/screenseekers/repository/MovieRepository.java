package com.webwizards.screenseekers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webwizards.screenseekers.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{
	List<Movie> findByTitle(String title);
}
