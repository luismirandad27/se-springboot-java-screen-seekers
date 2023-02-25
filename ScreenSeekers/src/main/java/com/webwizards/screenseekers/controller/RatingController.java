/*
 * Class File: RatingController.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will allow user to create rating and comment to movies based on movieId (BUT NOT WORKING, not sure if we need to use RatingSerialize and still confused how to use them)
 *  
 * 
 * @author Victor Chawsukho
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.model.Rating;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.RatingRepository;



@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class RatingController {

	@Autowired
	RatingRepository rep;
	
	@Autowired
	MovieRepository movRep;
	
//	@PostMapping("/movies/rating/{id}")
//	public ResponseEntity <Rating> createComment(@PathVariable("id") long id, @RequestBody Rating rating){
//		try {
//			Optional<Movie> movie = movRep.findById(id);
//			
//			if(movie.isPresent()) {
//				Movie m = movie.get();
//				rating.setUserRating(0);
//				rating.setComment("Hi");
//				
//				movRep.save(m);
//				rep.save(rating);
//			}
//			else
//			{
//				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//			}
//			
////			Rating myRating = rating;
////			rep.save(new Rating(rating.getUserRating(), rating.getComment()));
//			return new ResponseEntity<>(rating, HttpStatus.CREATED);
//			
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
//	@PutMapping("movies/rating/{id}")
//	public ResponseEntity<Rating> updateRating(@RequestBody Rating rating, @PathVariable Long id){
//		try {
//			Optional<Movie> myMovie = movRep.findById(id);
//			Optional<Rating> myRating = rep.findById(id);
//			if(movRep.findById(id).isPresent()) {
//				
//				Rating _myRating = myRating.get();
//				_myRating.setUserRating(rating.getUserRating());
//				_myRating.setComment(rating.getComment());
//				
//				rep.save(_myRating);
//				return new ResponseEntity<>(_myRating, HttpStatus.OK);
//			}else
//			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}catch(Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
//	@PutMapping("/{ratingId}/movie/{movieId}")
//	public ResponseEntity<Rating> updateRating(@PathVariable Long ratingId, @PathVariable Long movieId, @RequestBody Rating updatedRating) {
//	    Optional<Rating> optionalRating = rep.findById(ratingId);
//	    Optional<Movie> optionalMovie = movRep.findById(movieId);
//
//	    if (optionalRating.isPresent() && optionalMovie.isPresent()) {
//	        Rating rating = optionalRating.get();
//	        Movie movie = optionalMovie.get();
//
//	        rating.setUserRating(updatedRating.getUserRating());
//	        rating.setComment(updatedRating.getComment());
//	        rating.setMovie(movie);
////	        rating.setUpdatedAt(new Date());
//
//	        Rating savedRating = rep.save(rating);
//
//	        return new ResponseEntity<>(savedRating, HttpStatus.OK);
//	    } else {
//	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	    }
//	}
	
	@PostMapping("/movies/{movieId}/ratings")
	public ResponseEntity<?> addRating(@PathVariable long movieId, @RequestBody Rating rating){
	Optional<Movie> optionalMovie = movRep.findById(movieId);
	if (!optionalMovie.isPresent()) {
	    return ResponseEntity.notFound().build();
	}
	Movie movie = optionalMovie.get();
	rating.setMovie(movie);
	Rating savedRating = rep.save(rating);

	return ResponseEntity.ok(savedRating);
}



	
	@GetMapping("/movies/rating/{id}")
	public ResponseEntity<Movie> getMovie(@PathVariable("id") Long id){
		try {
			if(movRep.findById(id).isPresent()) {
				return new ResponseEntity<>(movRep.findById(id).get(), HttpStatus.FOUND);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping("/movies/rating")
	public ResponseEntity<List<Rating>> getAllRating(){
		try {
			List<Rating> ratings = rep.findAll();
			
			if(ratings.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(ratings, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 
	
}
