/*
 * Class File: MovieController.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will create end-point to interact with front-end. 
 * v1.01: added Find methods and random movies 
 * 
 * @author Victor Chawsukho
 * @version 1.01
 * 
 */

package com.webwizards.screenseekers.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.repository.MovieRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class MovieController {

	@Autowired
	MovieRepository rep;

	@GetMapping("/movies")
	@PreAuthorize("hasRole('ADMIN')") // @PreAuthorize not working, a person with user role can still access
	public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required = false) String title) {
		try {
			List<Movie> myList = new ArrayList<Movie>();
			if (title == null) {
				myList = rep.findAll();
			} else {
				myList = rep.findByTitleContainingIgnoreCase(title);
			}
			return new ResponseEntity<>(myList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// View movie information (Random) : basic info, where to watch, reviews
	@GetMapping("/movies/random")
	public ResponseEntity<List<Movie>> getRandomMovies(@RequestParam(required = false) String title) {
		try {
			List<Movie> myList = new ArrayList<Movie>();
			if (title == null) {
				myList = rep.findRandom();
			} else {
				myList = rep.findByTitleContainingIgnoreCase(title);
			}

			return new ResponseEntity<>(myList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/movies/searchByTitle")
	public ResponseEntity<List<Movie>> getMovieByTitle(@RequestParam(required = false) String title) {
		try {
			List<Movie> myList = new ArrayList<Movie>();
			if (title == null) {
				myList = rep.findAll();
			} else {
				myList = rep.findByTitleContainingIgnoreCase(title);
			}

			return new ResponseEntity<>(myList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/movies/searchByGenre")
	public ResponseEntity<List<Movie>> getMovieByGenre(@RequestParam(required = false) String genre) {
		try {
			List<Movie> myList = new ArrayList<Movie>();
			if (genre == null) {
				myList = rep.findAll();
			} else {
				myList = rep.findByGenreContainingIgnoreCase(genre);
			}

			return new ResponseEntity<>(myList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/movies/searchByReleaseDate")
	public ResponseEntity<List<Movie>> getMovieByReleaseDate(@RequestParam(required = false) int year) {
		try {
			List<Movie> myList = new ArrayList<Movie>();
			if (year == 0) {
				myList = rep.findAll();
			} else {
				myList = rep.findByReleaseDateYear(year);
			}

			return new ResponseEntity<>(myList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/movies/{id}")
	public ResponseEntity<Movie> getMovie(@PathVariable("id") Long id) {
		try {
			if (rep.findById(id).isPresent()) {
				return new ResponseEntity<>(rep.findById(id).get(), HttpStatus.FOUND);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/movies")
	public ResponseEntity<Movie> getMovie(@RequestBody Movie movie) {
		try {
			Movie myMovie = movie;
			rep.save(new Movie(movie.getTitle(), movie.getGenre(), movie.getReleaseDate(), movie.getLength(),
					movie.getSynopsis(), movie.getClassificationRating(), movie.getMovieTrailerLink(),
					movie.getCreatedAt(), movie.getUpdatedAt(), movie.getDeletedAt()));
			return new ResponseEntity<>(myMovie, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("movies/{id}")
	public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie, @PathVariable Long id) {
		try {
			Optional<Movie> myMovie = rep.findById(id);
			if (rep.findById(id).isPresent()) {
				Movie _myMovie = myMovie.get();
				_myMovie.setTitle(movie.getTitle());
				_myMovie.setGenre(movie.getGenre());
				_myMovie.setReleaseDate(movie.getReleaseDate());
				_myMovie.setLength(movie.getLength());
				_myMovie.setSynopsis(movie.getSynopsis());
				_myMovie.setClassificationRating(movie.getClassificationRating());
				_myMovie.setMovieTrailerLink(movie.getMovieTrailerLink());
				_myMovie.setCreatedAt(movie.getCreatedAt());
				_myMovie.setUpdatedAt(movie.getUpdatedAt());
				_myMovie.setDeletedAt(movie.getDeletedAt());
				rep.save(_myMovie);
				return new ResponseEntity<>(_myMovie, HttpStatus.OK);
			} else
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("movies/{id}")
	public ResponseEntity deleteMovie(@PathVariable Long id) {
		try {
			if (rep.findById(id).isPresent()) {
				rep.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/movies")
	public ResponseEntity deleteAllMovies() {
		try {
			rep.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
