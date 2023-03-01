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

import com.webwizards.screenseekers.model.Crew;
import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.repository.CrewRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class CrewController {
	@Autowired
	CrewRepository crewRepo;
	
	@GetMapping("/crew")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Crew>> getAllCrew(@RequestParam(required = false) String name) {
		try {
			List<Crew> myList = new ArrayList<Crew>();
			if (name == null) {
				myList = crewRepo.findAll();
			} else {
				myList = crewRepo.findByFirstName(name);
			}
			return new ResponseEntity<>(myList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/crew/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Crew> getACrew(@PathVariable("id") Long id) {
		try {
			if (crewRepo.findById(id).isPresent()) {
				return new ResponseEntity<>(crewRepo.findById(id).get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/crew")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Crew> createCrew(@RequestBody Crew crew) {
		try {
			Crew myCrew = crew;

			crewRepo.save(new Crew(crew.getFirstName(), crew.getLastName(), crew.getDateOfBirth(), crew.getNationality(), crew.getAward(), crew.getCreatedAt(), crew.getUpdatedAt(), crew.getDeletedAt()));

			return new ResponseEntity<>(myCrew, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("crew/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Crew> updateCrew(@RequestBody Crew crew, @PathVariable Long id) {
		try {
			Optional<Crew> myCrew = crewRepo.findById(id);
			if (crewRepo.findById(id).isPresent()) {
				Crew _myCrew = myCrew.get();
				_myCrew.setFirstName(crew.getFirstName());
				_myCrew.setLastName(crew.getLastName());
				_myCrew.setDateOfBirth(crew.getDateOfBirth());
				_myCrew.setNationality(crew.getNationality());
				_myCrew.setAward(crew.getAward());
				_myCrew.setCreatedAt(crew.getCreatedAt());
				_myCrew.setUpdatedAt(crew.getUpdatedAt());
				_myCrew.setDeletedAt(crew.getDeletedAt());
				crewRepo.save(_myCrew);
				return new ResponseEntity<>(_myCrew, HttpStatus.OK);
			} else
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("crew/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity deleteMovie(@PathVariable Long id) {
		try {
			if (crewRepo.findById(id).isPresent()) {
				crewRepo.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/crew")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity deleteAllMovies() {
		try {
			crewRepo.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
