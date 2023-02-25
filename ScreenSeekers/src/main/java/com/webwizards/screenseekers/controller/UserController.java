/*
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
 * 4) Enable/Disable a user
 * 
 * @author Luis Miguel Miranda
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.model.Rating;
import com.webwizards.screenseekers.model.User;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.RatingRepository;
import com.webwizards.screenseekers.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
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
	
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable long id){
		try{
			
			Optional<User> userResult = userRepo.findById(id);
			
			if (!userResult.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			User user = userResult.get();
			
			return new ResponseEntity<>(user,HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/user/{id}")
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
	
	@PutMapping("/user/{id}/disable")
	public ResponseEntity<User> disableUser(@PathVariable long id){
		try {
			
			Optional<User> user = userRepo.findById(id);
			
			if(!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			User updateUser = user.get();
			
			updateUser.setDeletedAt(new Date());
			
			userRepo.save(updateUser);
			
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/user/{id}/enable")
	public ResponseEntity<User> enableUser(@PathVariable long id){
		try {
			
			Optional<User> user = userRepo.findById(id);
			
			if(!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			User updateUser = user.get();
			
			updateUser.setUpdatedAt(new Date());
			updateUser.setDeletedAt(null);
			
			userRepo.save(updateUser);
			
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/user/{id}/updateProfileImage")
	public ResponseEntity<User> updateProfileImage(@PathVariable long id, @RequestBody Byte[] profileImage){
		try {
			
			Optional<User> user = userRepo.findById(id);
			
			if(!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			User updateUser = user.get();
			
			updateUser.setUpdatedAt(new Date());
			updateUser.setProfileImage(profileImage);
			
			userRepo.save(updateUser);
			
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
			
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
