/*
 * Class File: WatchlistDetailController.java
 * 
 * This class will store the API methods for a watchlistDetail:	
 * 				
 * 1)Add a new watchlistDetail Item to a watchlist (POST)		
 * 3)Get all watchlistDetail Items from a watchlist (GET)		
 * 2)Remove a watchlistDetail Item from a watchlist (DELETE)	
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.model.Watchlist;
import com.webwizards.screenseekers.model.WatchlistDetail;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.WatchlistDetailRepository;
import com.webwizards.screenseekers.repository.WatchlistRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:8081")
public class WatchlistDetailController {
	
	@Autowired
	WatchlistDetailRepository watchlistDetailRepo;
	
	@Autowired
	WatchlistRepository watchlistRepo;
	
	@Autowired
	MovieRepository movieRepo;
	

	@PostMapping("/watchlistdetail/{watchlistId}/movies/{movieId}")
	public ResponseEntity<WatchlistDetail> createWatchListDetailItem(@RequestBody WatchlistDetail watchlistDetail, @PathVariable Long watchlistId,
			@PathVariable Long movieId) {
		try {
			Optional<Watchlist> watchlist = watchlistRepo.findById(watchlistId);
			Optional<Movie> movie = movieRepo.findById(movieId);
			
			if(!watchlist.isPresent() || !movie.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			WatchlistDetail watchlistDetailObj = new WatchlistDetail(watchlistDetail.getWatchlist(), watchlistDetail.getMovie());
			watchlistDetailObj.setMovie(movie.get());
			watchlistDetailObj.setWatchlist(watchlist.get());
			watchlistDetailRepo.save(watchlistDetailObj);

			return new ResponseEntity<>(watchlistDetailObj, HttpStatus.OK);
			
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/watchlists/watchlistdetail/{watchlistId}")
	public ResponseEntity<List<WatchlistDetail>> getAllWtchlistDetailItems(@PathVariable Long watchlistId){
		try {
			List<WatchlistDetail> listWatchlistDetail = new ArrayList<WatchlistDetail>();
			watchlistDetailRepo.findAll().forEach(listWatchlistDetail::add);
			
			if(listWatchlistDetail.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} 
			
			return new ResponseEntity<>(listWatchlistDetail, HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

	@DeleteMapping("/watchlistdetail/{watchlistId}/movies/{movieId}")
	public ResponseEntity<WatchlistDetail> deleteWatchlistDetailItem(@PathVariable Long watchlistId, @PathVariable Long movieId){
		try {
			if(watchlistDetailRepo.findById(movieId).isPresent()) {
				WatchlistDetail watchlistDetailObj = watchlistDetailRepo.findById(movieId).get();
				watchlistDetailRepo.deleteById(movieId);
				watchlistDetailObj.setDeletedAt(new Date());
				
				return new ResponseEntity<>	(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
