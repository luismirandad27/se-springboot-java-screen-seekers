/*
 * Class File: WatchlistController.java
 * 
 * This class will store the API methods for a watchlist:
 * 
 * 1)Display all watchlists from the database (GET)					
 * 2)Display all watchlists from a user ID (GET)					
 * 3)Display a watchlists from a watchlist ID (GET)					
 * 4)Add a new watchlist (POST)				
 * 5)Update a watchlist main info (PUT)					
 * 6)Remove an entire watchlist from a User (DELETE)	
 * 
 * @author Marc Deroca
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
import com.webwizards.screenseekers.model.Watchlist;
import com.webwizards.screenseekers.model.WatchlistDetail;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.UserRepository;
import com.webwizards.screenseekers.repository.WatchlistDetailRepository;
import com.webwizards.screenseekers.repository.WatchlistRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:8081")
public class WatchlistController {
	
	@Autowired
	WatchlistRepository watchlistRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	WatchlistDetailRepository watchlistDetailRepo;
	

	@GetMapping("/watchlists")
	public ResponseEntity<List<Watchlist>> getAllWatchlists(){
		try {
			
			List<Watchlist> watchlists = new ArrayList<Watchlist>();
			watchlistRepo.findAll().forEach(watchlists::add);
			
			if(watchlists.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(watchlists, HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/watchlists/{id}")
	public ResponseEntity<Watchlist> getWatchlist(@PathVariable long id){
		try {
			
			if(watchlistRepo.findById(id).isPresent()) {
				return new ResponseEntity<>(watchlistRepo.findById(id).get(), HttpStatus.OK);
			}
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@PostMapping("/watchlists/{id}")
	public ResponseEntity<Watchlist> createWatchList(@RequestBody Watchlist watchlist, @PathVariable Long id) {
		try {
			Optional<User> user = userRepo.findById(id);
			
			if(!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} 
			
			User userObj = user.get();
			
			Watchlist newWatchlist = new Watchlist(watchlist.getName(), userObj);
			newWatchlist.setCreatedAt(new Date());
			
			watchlistRepo.save(newWatchlist);
			return new ResponseEntity<>(newWatchlist, HttpStatus.CREATED);
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	

	@PutMapping("/watchlists/{id}")
	public ResponseEntity<Watchlist> updateWatchlist(@RequestBody Watchlist newWatchlist,
			@PathVariable Long id){
		try {
			
			if(watchlistRepo.findById(id).isPresent()) {
				Watchlist myWatchlist = watchlistRepo.findById(id).get();
				
				//update main info 
				myWatchlist.setName(newWatchlist.getName());
				myWatchlist.setUpdatedAt(new Date());
				
				watchlistRepo.save(myWatchlist);
				
				return new ResponseEntity<>(myWatchlist, HttpStatus.OK);
				
				
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	 
	@DeleteMapping("/watchlists/{id}")
	public ResponseEntity<Watchlist> deleteWatchlist(@PathVariable Long id){
		try {
			if(watchlistRepo.findById(id).isPresent()) {
				Watchlist myWatchlist = watchlistRepo.findById(id).get();
				watchlistRepo.deleteById(id);
				myWatchlist.setDeletedAt(new Date());
				
				return new ResponseEntity<>	(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
}
