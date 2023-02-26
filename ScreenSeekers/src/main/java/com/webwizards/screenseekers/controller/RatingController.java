/*
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
 * 3) Get rating and comment based on the userId
 * 4) Update the rating and comment
 * 
 * @author Victor Chawsukho
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.webwizards.screenseekers.model.RatingSerializer;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.RatingRepository;



@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class RatingController {

	@Autowired
	RatingRepository ratingRepo;
	
	@Autowired
	MovieRepository movieRepo;

	
	@PostMapping("/movies/rating")
	public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
		try {
			Rating myRating = rating;
			ratingRepo.save(new Rating(rating.getUserRating(), rating.getComment()));
			
			return new ResponseEntity<>(myRating, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	
		@GetMapping("/movies/rating")
		public ResponseEntity<List<Rating>> getAllRating(@RequestParam(required = false) Long userId) {
			try {
				List<Rating> myRate = new ArrayList<Rating>();
				if (userId == null) {
					ratingRepo.findAll().forEach(myRate::add);
				} else {
					ratingRepo.findAllByUserId(userId).forEach(myRate::add);
				}

				return new ResponseEntity<>(myRate, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		
	@GetMapping("/movies/rating/{userId}")
	public ResponseEntity<Rating> getRatingByUserId(@PathVariable("userId") Long userId){
		try {
			Optional<Rating> ratingOptional = ratingRepo.findByUserId(userId);
			
			if(ratingOptional.isPresent()) {
				return new ResponseEntity<>(ratingOptional.get(), HttpStatus.FOUND);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("movies/rating/{userId}")
	public ResponseEntity<Rating> updateRating(@RequestBody Rating rating, @PathVariable Long userId) {
		try {
			Optional<Rating> myRating = ratingRepo.findByUserId(userId);
			if(ratingRepo.findByUserId(userId).isPresent()) {
				Rating _myRating = myRating.get();
				_myRating.setUserRating(rating.getUserRating());
				_myRating.setComment(rating.getComment());
				ratingRepo.save(_myRating);
				return new ResponseEntity<>(_myRating, HttpStatus.OK);
			} else
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
}
