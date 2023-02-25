package com.webwizards.screenseekers.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

//import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.webwizards.screenseekers.model.User;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.UserRepository;
import com.webwizards.screenseekers.utils.Recommender;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:8081")
public class MovieController {
	
	@Autowired
	MovieRepository rep;
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/movies")
	public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required=false) String title){
		try {
			List<Movie> myList = new ArrayList<Movie>();
			if(title==null) {
				myList=rep.findAll();
			}else {
				myList=rep.findByTitle(title);
			}
			return new ResponseEntity<>(myList, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/movies/{id}")
	public ResponseEntity<Movie> getMovie(@PathVariable("id") Long id){
		try {
			if(rep.findById(id).isPresent()) {
				return new ResponseEntity<>(rep.findById(id).get(), HttpStatus.FOUND);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/movies")
	public ResponseEntity<Movie> getMovie(@RequestBody Movie movie){
		try {
			Movie myMovie = movie;
			rep.save(new Movie(movie.getTitle(), movie.getGenre(), movie.getReleaseDate(), movie.getLength(), movie.getSynopsis(), movie.getClassificationRating(), movie.getMovieTrailerLink(), movie.getCreatedAt(), movie.getUpdatedAt(), movie.getDeletedAt()));
			return new ResponseEntity<>(myMovie, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("movies/{id}")
	public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie, @PathVariable Long id){
		try {
			Optional<Movie> myMovie = rep.findById(id);
			if(rep.findById(id).isPresent()) {
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
			}else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("movies/{id}")
	public ResponseEntity deleteMovie(@PathVariable Long id){
		try {
			if (rep.findById(id).isPresent()) {
				rep.deleteById(id);
				return new ResponseEntity<>	(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/movies")
	public ResponseEntity deleteAllMovies() {
		try {
			rep.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/movies/recommend")
	public ResponseEntity<HashMap<Long,HashMap<Long,Double>>> recommendMoviesToUser(){
		
		try {
			
			//Getting All Movies
			List<Movie> allMovies = rep.findAllMoviesAvailable();
			
			//Get the User Info
			//Optional<User> user = userRepo.findById(1);
			
			if (allMovies.isEmpty() /*|| !user.isPresent()*/) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			Recommender movieRecommender = new Recommender();
			
			//Let's create the HashMap
			movieRecommender.setRatings(allMovies);
			
			movieRecommender.displayHashMap();
			
			HashMap<Long,HashMap<Long,Double>> x = movieRecommender.getRatings();
			
			//HashMap<Long,HashMap<Long,Double>> ratings = 
			return new ResponseEntity<>(x,HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
