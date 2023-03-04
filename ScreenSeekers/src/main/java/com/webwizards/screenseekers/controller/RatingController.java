/**
 * Class File: RatingController.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the API methods with regards of the Rating and Comments by users.
 * That includes:
 * 
 * 1) Create rating and comment
 * 2) Retrieve all rating and comment 
 * 3) Get all ratings from a user
 * 4) Get all ratings from a movie
 * 5) Delete ratings made by a user
 * 6) Delete ratings made to a movie
 * 7) Delete rating by ID
 * 8) Delete all ratings
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
import com.webwizards.screenseekers.model.Rating;
import com.webwizards.screenseekers.model.User;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.RatingRepository;
import com.webwizards.screenseekers.repository.UserRepository;
import com.webwizards.screenseekers.utils.ResponseMessage;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class RatingController {

	@Autowired
	RatingRepository ratingRepo;
	
	@Autowired
	MovieRepository movieRepo;
	
	@Autowired
	UserRepository userRepo;

	
	@PostMapping("/users/{userId}/ratings/{movieId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Rating> createRating(@RequestBody Rating rating, @PathVariable long userId, @PathVariable long movieId) {
		try {
			
			Optional<User> user = userRepo.findById(userId);
			Optional<Movie> movie = movieRepo.findById(movieId);
			
			if (!user.isPresent() || !movie.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			Rating newRating = new Rating(rating.getUserRating(), rating.getComment());
			newRating.setMovie(movie.get());
			newRating.setUser(user.get());
			
			ratingRepo.save(newRating);
			
			return new ResponseEntity<>(newRating, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/users/{userId}/ratings/{movieId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Rating> updateRating(@RequestBody Rating rating, @PathVariable Long userId, @PathVariable Long movieId) {
		try {
			
			Optional<Rating> myRating = ratingRepo.findByUserIdAndMovieId(userId,movieId);
			
			if(myRating.isPresent()) {
				
				Rating _myRating = myRating.get();
				_myRating.setUserRating(rating.getUserRating());
				_myRating.setComment(rating.getComment());
				_myRating.setUpdatedAt(new Date());
				
				ratingRepo.save(_myRating);
				
				return new ResponseEntity<>(_myRating, HttpStatus.OK);
			} else
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/users/ratings")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Rating>> getAllRatings() {
		try {
			
			List<Rating> myRate = new ArrayList<Rating>();
			
			ratingRepo.findAll().forEach(myRate::add);
			
			return new ResponseEntity<>(myRate, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/users/{userId}/ratings")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Rating>> getRatingsByUser(@PathVariable Long userId) {
		try {
			
			List<Rating> myRatings = ratingRepo.findByUserId(userId);
			
			if(!myRatings.isEmpty()) {
				
				return new ResponseEntity<>(myRatings, HttpStatus.OK);
				
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
				
				
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/users/ratings/{movieId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Rating>> getRatingsByMovie(@PathVariable Long movieId) {
		try {
			
			List<Rating> myRatings = ratingRepo.findByMovieId(movieId);
			
			if(!myRatings.isEmpty()) {
				
				return new ResponseEntity<>(myRatings, HttpStatus.OK);
				
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
				
				
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/users/{userId}/remove-ratings")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ResponseMessage> deleteRatingsByUser(@PathVariable Long userId) {
		try {
			
			List<Rating> myRatings = ratingRepo.findByUserId(userId);
			
			if(!myRatings.isEmpty()) {
				
				ratingRepo.deleteByUserId(userId);
				
				String message = "All Ratings from user id: "+userId+" have been deleted successfully!";
				
				return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
				
			} else
			{
				return new ResponseEntity<>( HttpStatus.NO_CONTENT);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/movies/{movieId}/remove-ratings")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ResponseMessage> deleteRatingsByMovie(@PathVariable Long movieId) {
		try {
			
			List<Rating> myRatings = ratingRepo.findByMovieId(movieId);
			
			if(!myRatings.isEmpty()) {
				
				ratingRepo.deleteByMovieId(movieId);
				
				String message = "All Ratings from movie id: "+movieId+" have been deleted successfully!";
				
				return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
			} else
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/users/ratings/{ratingId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ResponseMessage> deleteRatingById(@PathVariable Long ratingId) {
		try {
			
			Optional<Rating> myRating = ratingRepo.findById(ratingId);
			
			if(myRating.isPresent()) {
				
				ratingRepo.deleteById(ratingId);
				
				String message = "Rating has been deleted successfully!";
				
				return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
			} else
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/users/ratings")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseMessage> deleteAllRatings() {
		try {
			
			List<Rating> myRatings = ratingRepo.findAll();
			
			if(!myRatings.isEmpty()) {
				
				ratingRepo.deleteAll();
				
				String message = "All Ratings have been deleted successfully!";
				
				return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
			} else
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
