/**
 * Class File: CrewController.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the API methods with regards of the Crew member for a movie.
 * That includes:
 * 
 * 1) Get all the crew member from the database or by name of the crew member
 * 2) Retrieve a crew member by id
 * 3) Create a new crew member from the database
 * 4) Update the crew member information
 * 5) Delete a crew member from the database by Id
 * 6) Delete all crew members from the database
 * 
 * @author Regal Cruz
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

import com.webwizards.screenseekers.model.Crew;
import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.repository.CrewRepository;
import com.webwizards.screenseekers.utils.ResponseMessage;

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
			
			Crew myCrew = new Crew(crew.getFirstName(), crew.getLastName(), crew.getDateOfBirth(), crew.getNationality(), crew.getAward());

			crewRepo.save(myCrew);

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
	public ResponseEntity<ResponseMessage> deleteCrew(@PathVariable Long id) {
		try {
			
			Optional<Crew> crew = crewRepo.findById(id);
			
			if (crew.isPresent()) {
				
				String message = "Crew member: "+crew.get().getFirstName()+" "+crew.get().getLastName() + " has been deleted successfully!";
				
				crewRepo.deleteById(id);
				
				return new ResponseEntity<>(new ResponseMessage(message),HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
				
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/crew")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseMessage> deleteAllCrews() {
		try {
			
			crewRepo.deleteAll();
			
			String message = "All crews information has been deleted from the database successfully!";
			
			return new ResponseEntity<>(new ResponseMessage(message),HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
