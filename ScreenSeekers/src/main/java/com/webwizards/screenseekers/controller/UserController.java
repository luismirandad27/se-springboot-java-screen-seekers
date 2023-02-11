package com.webwizards.screenseekers.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webwizards.screenseekers.model.User;
import com.webwizards.screenseekers.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository userRepo;
	
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
			
			Optional<User> user = userRepo.findById(id);
			
			if (!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			else {
				return new ResponseEntity<>(user.get(),HttpStatus.OK);
			}
			
		}catch(Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User newUserInfo){
		
		try {
			
			Optional<User> user = userRepo.findById(id);
			
			if(!user.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			user.get().setFirstName(newUserInfo.getFirstName());
			user.get().setLastName(newUserInfo.getLastName());
			user.get().setDateOfBirth(newUserInfo.getDateOfBirth());
			user.get().setPhone(newUserInfo.getPhone());
			user.get().setAddress(newUserInfo.getAddress());
			user.get().setCity(newUserInfo.getCity());
			user.get().setProvince(newUserInfo.getProvince());
			user.get().setCountry(newUserInfo.getCountry());
			user.get().setEmail(newUserInfo.getEmail());
			
			return null;
			
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
}
