/*
 * Class File: WatchlistController.java
 * 
 * This class will store the API methods for a watchlist:
 * 
 * 1) Display all watchlists from the database					
 * 2) Display all watchlists from a watchlist ID					
 * 3) Display a watchlists from a USER			
 * 4) Add a new watchlist				
 * 5) Update a watchlist main info
 * 6) Delete a watchlist by ID
 * 7) Delete watchlists of by USER
 * 8) Delete all watchlists
 * 9) Add a movie into a watchlist
 * 10) Remove a movie from a watchlist
 * 
 * @author Marc Deroca, Luis Miguel Miranda
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
import com.webwizards.screenseekers.model.User;
import com.webwizards.screenseekers.model.Watchlist;
import com.webwizards.screenseekers.model.WatchlistDetail;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.UserRepository;
import com.webwizards.screenseekers.repository.WatchlistDetailRepository;
import com.webwizards.screenseekers.repository.WatchlistRepository;
import com.webwizards.screenseekers.utils.ResponseMessage;

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
	
	@Autowired
	MovieRepository movieRepo;
	

	@GetMapping("/users/watchlists")
	@PreAuthorize("hasRole('ADMIN')")
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
	
	
	@GetMapping("/users/watchlists/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Watchlist> getWatchlist(@PathVariable long id){
		try {
			
			if(watchlistRepo.findById(id).isPresent()) {
				return new ResponseEntity<>(watchlistRepo.findById(id).get(), HttpStatus.OK);
			}
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/users/{userId}/watchlists")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Watchlist>> getWatchlistByUser(@PathVariable long userId){
		try {
			
			Optional<User> user = userRepo.findById(userId);
			
			if(!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			List<Watchlist> watchlist = watchlistRepo.findByUserId(userId);
			
			if(watchlist.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(watchlist,HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/users/{userId}/watchlists")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Watchlist> createWatchList(@RequestBody Watchlist watchlist, @PathVariable Long userId) {
		try {
			Optional<User> user = userRepo.findById(userId);
			
			if(!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} 
			
			User userObj = user.get();
			
			Watchlist newWatchlist = new Watchlist(watchlist.getName(), userObj);
			newWatchlist.setCreatedAt(new Date());
			
			watchlistRepo.save(newWatchlist);
			return new ResponseEntity<>(newWatchlist, HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/users/watchlists/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Watchlist> updateWatchlist(@RequestBody Watchlist newWatchlist,
			@PathVariable Long id){
		
		try {
			
			Optional<Watchlist> watchlist = watchlistRepo.findById(id);
			
			if(watchlist.isPresent()) {
				
				Watchlist watchlistObj = watchlist.get();
				
				//update main info 
				watchlistObj.setName(newWatchlist.getName());
				watchlistObj.setUpdatedAt(new Date());
				
				watchlistRepo.save(watchlistObj);
				
				return new ResponseEntity<>(watchlistObj, HttpStatus.OK);
				
				
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	 
	@DeleteMapping("/users/watchlists/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ResponseMessage> deleteWatchlist(@PathVariable Long id){
		try {
			
			if(watchlistRepo.findById(id).isPresent()) {
				
				Watchlist myWatchlist = watchlistRepo.findById(id).get();
				
				watchlistRepo.deleteById(id);
				
				String message = "Watchlist "+myWatchlist.getName()+" has been deleted successfully!";
				
				return new ResponseEntity<>	(new ResponseMessage(message),HttpStatus.OK);
				
			}else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/users/{userId}/watchlists")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ResponseMessage> deleteWatchlistsByUser(@PathVariable Long userId){
		try {
			
			Optional<User> user = userRepo.findById(userId);
			
			if(!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			watchlistRepo.deleteByUserId(userId);
			
			String message = "All the watchlists has been removed from the user";
			
			return new ResponseEntity<>(new ResponseMessage(message),HttpStatus.OK);
			
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/users/watchlists")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseMessage> deleteAllWatchlists(){
		
		try {
			
			List<Watchlist> watchlists = watchlistRepo.findAll();
			
			if(watchlists.isEmpty()) {
				
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				
			}
			
			watchlistRepo.deleteAll();
			
			String message = "All the watchlists has been removed from the database successfully!";
			
			return new ResponseEntity<>(new ResponseMessage(message),HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("users/watchlists/{watchlistId}/add-movie/{movieId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<WatchlistDetail> createWatchListDetailItem(@PathVariable Long watchlistId,
			@PathVariable Long movieId) {
		try {
			Optional<Watchlist> watchlist = watchlistRepo.findById(watchlistId);
			Optional<Movie> movie = movieRepo.findById(movieId);
			
			if(!watchlist.isPresent() || !movie.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			WatchlistDetail watchlistDetailObj = new WatchlistDetail();
			watchlistDetailObj.setMovie(movie.get());
			watchlistDetailObj.setWatchlist(watchlist.get());
			watchlistDetailRepo.save(watchlistDetailObj);

			return new ResponseEntity<>(watchlistDetailObj, HttpStatus.OK);
			
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("users/watchlists/{watchlistId}/remove-movie/{movieId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ResponseMessage> deleteWatchListDetailItemByMovie(@PathVariable Long watchlistId,
			@PathVariable Long movieId) {
		try {
			
			Optional<WatchlistDetail> watchlistDetail = watchlistDetailRepo.findByWatchlistIdAndMovieId(watchlistId,movieId);
			
			if(!watchlistDetail.isPresent() ) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			watchlistDetailRepo.deleteById(watchlistDetail.get().getId());
			
			String message = "The movie id "+watchlistDetail.get().getMovie().getId()+
							" has been removed from watchlist id "+watchlistDetail.get().getWatchlist().getId()+"!";

			return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
			
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}