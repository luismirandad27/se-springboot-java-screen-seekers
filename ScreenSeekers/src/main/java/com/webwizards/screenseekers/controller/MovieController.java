package com.webwizards.screenseekers.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.repository.MovieRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:8081")
public class MovieController {
	
	@Autowired
	MovieRepository rep;
	
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
}
