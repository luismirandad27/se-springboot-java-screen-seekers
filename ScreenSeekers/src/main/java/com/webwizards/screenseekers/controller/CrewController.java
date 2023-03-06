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
 * 7) Add a crew member into a movie's crew
 * 8) Remove a crew member from a movie's crew
 * 9) Add a crew member image
 * 
 * @author Regal Cruz, Luis Miguel Miranda
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
import org.springframework.util.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.webwizards.screenseekers.model.CrewMember;
import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.model.ProductionCrew;
import com.webwizards.screenseekers.model.User;
import com.webwizards.screenseekers.repository.CrewMemberRepository;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.ProductionCrewRepository;
import com.webwizards.screenseekers.service.FileUploadUtil;
import com.webwizards.screenseekers.utils.ResponseMessage;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class CrewController {
	
	@Autowired
	CrewMemberRepository crewRepo;
	
	@Autowired
	MovieRepository movieRepo;
	
	@Autowired
	ProductionCrewRepository productionCrewRepo;
	
	@GetMapping("/movies/crew-members")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<CrewMember>> getAllCrew(@RequestParam(required = false) String name) {
		try {
			
			List<CrewMember> myList = new ArrayList<CrewMember>();
			
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
	
	@GetMapping("/movies/crew-members/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CrewMember> getACrew(@PathVariable("id") Long id) {
		try {
			if (crewRepo.findById(id).isPresent()) {
				
				return new ResponseEntity<>(crewRepo.findById(id).get(), HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/movies/{movieId}/crew-members")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ProductionCrew>> getProductionCrewByMovie(@PathVariable Long movieId) {
		try {
			
			List<ProductionCrew> productionCrewList = productionCrewRepo.findByMovieId(movieId);
			
			List<ProductionCrew> productionCrewListResponse = new ArrayList<>();
			
			if(!productionCrewList.isEmpty()) {
				
				for (ProductionCrew productionCrewMember: productionCrewList) {
					
					productionCrewMember.setMovie(null);
					productionCrewListResponse.add(productionCrewMember);
					
				}
				
				return new ResponseEntity<>(productionCrewListResponse,HttpStatus.OK);
			}
			
			return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/movies/crew-members")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CrewMember> createCrew(@RequestBody CrewMember crew) {
		try {
			
			CrewMember myCrew = new CrewMember(crew.getFirstName(), crew.getLastName(), crew.getDateOfBirth(), crew.getNationality(), crew.getAward());

			crewRepo.save(myCrew);

			return new ResponseEntity<>(myCrew, HttpStatus.CREATED);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/movies/crew-members/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CrewMember> updateCrew(@RequestBody CrewMember crew, @PathVariable Long id) {
		try {
			Optional<CrewMember> myCrew = crewRepo.findById(id);
			
			if (myCrew.isPresent()) {
				
				CrewMember _myCrew = myCrew.get();
				
				_myCrew.setFirstName(crew.getFirstName());
				_myCrew.setLastName(crew.getLastName());
				_myCrew.setDateOfBirth(crew.getDateOfBirth());
				_myCrew.setNationality(crew.getNationality());
				_myCrew.setAward(crew.getAward());
				_myCrew.setUpdatedAt(new Date());
				
				crewRepo.save(_myCrew);
				
				return new ResponseEntity<>(_myCrew, HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				
			}
				
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/movies/crew-members/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseMessage> deleteCrew(@PathVariable Long id) {
		try {
			
			Optional<CrewMember> crew = crewRepo.findById(id);
			
			if (crew.isPresent()) {
				
				String message = "Crew member: "+crew.get().getFirstName()+" "+crew.get().getLastName() + " has been deleted successfully!";
				
				crewRepo.deleteById(id);
				
				return new ResponseEntity<>(new ResponseMessage(message),HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/movies/crew-members")
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
	
	@PostMapping("/movies/{movieId}/add-crew-member/{crewId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductionCrew> addCrewMemberToMovie(@PathVariable long movieId, @PathVariable long crewId,@RequestBody ProductionCrew productionCrew){
		
		try {
			
			Optional<Movie> movie = movieRepo.findById(movieId);
			Optional<CrewMember> crew = crewRepo.findById(crewId);
			
			if (!movie.isPresent() || !crew.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			ProductionCrew prodCrew = new ProductionCrew(
					productionCrew.getMovieRole(),
					productionCrew.getCharacterName());
			
			prodCrew.setMovie(movie.get());
			prodCrew.setCrewMember(crew.get());
			
			productionCrewRepo.save(prodCrew);
			
			return new ResponseEntity<>(prodCrew,HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/movies/{movieId}/remove-crew-member/{crewId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseMessage> removeCrewMemberToMovie(@PathVariable long movieId, @PathVariable long crewId){
		
		try {
			
			Optional<ProductionCrew> productionCrew = productionCrewRepo.findByMovieIdAndCrewMemberId(movieId,crewId);
			
			if (!productionCrew.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			productionCrewRepo.deleteById(productionCrew.get().getId());
			
			String message = "Crew member with id "+productionCrew.get().getCrewMember().getId()+
							" has been removed from movie "+productionCrew.get().getMovie().getId()+"!";
			
			return new ResponseEntity<>(new ResponseMessage(message),HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/movies/crew-members/{id}/upload-profile-image")
	@PreAuthorize("hasRole('ADMIN')" )
	public ResponseEntity<CrewMember> uploadProfileImage(@PathVariable long id, @RequestParam("file") MultipartFile profileImageFile){
		try {
			
			Optional<CrewMember> crewMember = crewRepo.findById(id);
			
			if(!crewMember.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			String fileName = StringUtils.cleanPath(profileImageFile.getOriginalFilename());
			
			CrewMember updatedCrewMember = crewMember.get();
			
			updatedCrewMember.setProfileImage(fileName);
			
			updatedCrewMember.setUpdatedAt(new Date());
			
			crewRepo.save(updatedCrewMember);
			
			String uploadDir = "crew-photos/" + updatedCrewMember.getId();
			
			FileUploadUtil.saveFile(uploadDir, fileName, profileImageFile);
			
			return new ResponseEntity<>(updatedCrewMember, HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
