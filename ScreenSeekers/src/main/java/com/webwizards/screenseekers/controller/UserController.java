/**
 * Class File: UserController.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the API methods with regards of the User profile management.
 * That includes:
 * 
 * 1) Retrieving all users
 * 2) Get user based on the id
 * 3) Update the information of a user
 * 4) Disable a user's account
 * 5) Enable a user's account
 * 6) Recommend a set of movies of a user (by user id)
 * 7) Upload a profile image
 * 
 * @author Luis Miguel Miranda
 * @version 1.0
 * 
 **/

package com.webwizards.screenseekers.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.model.User;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.RatingRepository;
import com.webwizards.screenseekers.repository.UserRepository;
import com.webwizards.screenseekers.service.FileUploadUtil;
import com.webwizards.screenseekers.utils.Recommender;
import com.webwizards.screenseekers.utils.ResponseMessage;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:8081")
public class UserController {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RatingRepository ratingRepo;
	
	@Autowired
	MovieRepository movieRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')" )
	public ResponseEntity<List<User>> getAllUsers(){
		try {
			
			List<User> users = new ArrayList<>();
			userRepo.findAll().forEach(users::add);
			
			if(users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(users, HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/users/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')" )
	public ResponseEntity<User> getUser(@PathVariable long id){
		try{
			
			Optional<User> userResult = userRepo.findById(id);
			
			if (!userResult.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			User user = userResult.get();
			
			return new ResponseEntity<>(user,HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/users/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')" )
	public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User newUserInfo){
		
		try {
			
			Optional<User> user = userRepo.findById(id);
			
			if(!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			User currentUser = user.get();
			
			//Changing basic information of the user
			currentUser.setFirstName(newUserInfo.getFirstName());
			currentUser.setLastName(newUserInfo.getLastName());
			currentUser.setDateOfBirth(newUserInfo.getDateOfBirth());
			currentUser.setPhone(newUserInfo.getPhone());
			currentUser.setAddress(newUserInfo.getAddress());
			currentUser.setCity(newUserInfo.getCity());
			currentUser.setProvince(newUserInfo.getProvince());
			currentUser.setCountry(newUserInfo.getCountry());
			currentUser.setEmail(newUserInfo.getEmail());
			
			currentUser.setUpdatedAt(new Date());
			
			//Changing password
			currentUser.setPassword(encoder.encode(newUserInfo.getPassword()));
			
			//Store the new User information
			userRepo.save(currentUser);
			
			//Return the User information (response)
			return new ResponseEntity<>(currentUser,HttpStatus.OK);
			
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/users/{id}/disable")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')" )
	public ResponseEntity<ResponseMessage> disableUser(@PathVariable long id){
		try {
			
			Optional<User> user = userRepo.findById(id);
			
			if(!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			User updateUser = user.get();
			
			updateUser.setDeletedAt(new Date());
			
			userRepo.save(updateUser);
			
			String message = "User account with id "+updateUser.getId()+" has been disabled successfully!";
			
			return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/users/{id}/enable")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')" )
	public ResponseEntity<ResponseMessage> enableUser(@PathVariable long id){
		try {
			
			Optional<User> user = userRepo.findById(id);
			
			if(!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			User updateUser = user.get();
			
			updateUser.setUpdatedAt(new Date());
			updateUser.setDeletedAt(null);
			
			userRepo.save(updateUser);
			
			String message = "User account with id "+updateUser.getId()+" has been enabled successfully!";
			
			return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/users/{id}/recommend")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Movie>> recommendMoviesToUser(@PathVariable long id){
		
		try {
			
			//Getting All Movies
			List<Movie> allMovies = movieRepo.findAll();
			
			//Getting All Available Ratings
			List<User> allUsers = userRepo.findAllUsersAvailable();
			
			//Get the User Info
			Optional<User> user = userRepo.findById(id);
			
			if (allMovies.isEmpty() || !user.isPresent() || allUsers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			Recommender movieRecommender = new Recommender();
			
			//Let's create the HashMap
			movieRecommender.setRatings(allUsers);
			
			List<Movie> recommendations = movieRecommender.getRecommendedMovieList(allMovies, id);
			
			if (recommendations.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(recommendations,HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/users/{id}/upload-profile-image")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')" )
	public ResponseEntity<User> uploadProfileImage(@PathVariable long id, @RequestParam("file") MultipartFile profileImageFile){
		try {
			
			Optional<User> user = userRepo.findById(id);
			
			if(!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			String fileName = StringUtils.cleanPath(profileImageFile.getOriginalFilename());
			
			User updateUser = user.get();
			
			updateUser.setProfileImage(fileName);
			
			updateUser.setUpdatedAt(new Date());
			
			userRepo.save(updateUser);
			
			String uploadDir = "resources/user-photos/" + updateUser.getId();
			
			FileUploadUtil.saveFile(uploadDir, fileName, profileImageFile);
			
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
