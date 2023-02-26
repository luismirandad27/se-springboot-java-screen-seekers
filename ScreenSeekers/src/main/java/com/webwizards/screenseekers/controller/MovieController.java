/*
 * Class File: RatingController.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the API methods with regards of the movies information management.
 * That includes:
 * 
 * 1) Retrieve all movies accessed by Admin
 * 2) Retrieve random movies  
 * 3) Get movies based on the Title
 * 4) Get movies based on the Genre
 * 5) Get movies based on the ReleaseDate 
 * 6) Get movies based on the id
 * 7) Create movies
 * 8) Update the movie information
 * 9) Delete movies based on id
 * 10) Delete all movies
 * 
 * @author Victor Chawsukho
 * @version 1.01
 * 
 */

package com.webwizards.screenseekers.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
	MovieRepository movieRepo;

	@GetMapping("/movies")
	@PreAuthorize("hasRole('ADMIN')") // @PreAuthorize not working, a person with user role can still access
	public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required = false) String title) {
		try {
			List<Movie> myList = new ArrayList<Movie>();
			if (title == null) {
				myList = movieRepo.findAll();
			} else {
				myList = movieRepo.findByTitleContainingIgnoreCase(title);
			}
			return new ResponseEntity<>(myList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping("/movies/random")
	public ResponseEntity<List<Movie>> getRandomMovies(@RequestParam(required = false) String title) {
		try {
			List<Movie> myList = new ArrayList<Movie>();
			if (title == null) {
				myList = movieRepo.findRandom();
			} else {
				myList = movieRepo.findByTitleContainingIgnoreCase(title);
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
				myList = movieRepo.findAll();
			} else {
				myList = movieRepo.findByTitleContainingIgnoreCase(title);
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
				myList = movieRepo.findAll();
			} else {
				myList = movieRepo.findByGenreContainingIgnoreCase(genre);
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
				myList = movieRepo.findAll();
			} else {
				myList = movieRepo.findByReleaseDateYear(year);
			}

			return new ResponseEntity<>(myList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/movies/{id}")
	public ResponseEntity<Movie> getMovie(@PathVariable("id") Long id) {
		try {
			if (movieRepo.findById(id).isPresent()) {
				return new ResponseEntity<>(movieRepo.findById(id).get(), HttpStatus.FOUND);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/movies")
	public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
		try {
			Movie myMovie = movie;
			movieRepo.save(new Movie(movie.getTitle(), movie.getGenre(), movie.getReleaseDate(), movie.getLength(),
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
			Optional<Movie> myMovie = movieRepo.findById(id);
			if (movieRepo.findById(id).isPresent()) {
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
				movieRepo.save(_myMovie);
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
			if (movieRepo.findById(id).isPresent()) {
				movieRepo.deleteById(id);
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
			movieRepo.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
