/**
 * Class File: AuthController.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the API methods for login and signup
 * 
 * @author Luis Miguel Miranda
 * @version 1.0
 * 
 **/

package com.webwizards.screenseekers.controller;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webwizards.screenseekers.model.ERole;
import com.webwizards.screenseekers.model.Role;
import com.webwizards.screenseekers.model.User;
import com.webwizards.screenseekers.payload.request.LoginRequest;
import com.webwizards.screenseekers.payload.request.SignUpRequest;
import com.webwizards.screenseekers.payload.response.JwtResponse;
import com.webwizards.screenseekers.payload.response.MessageResponse;
import com.webwizards.screenseekers.repository.RoleRepository;
import com.webwizards.screenseekers.repository.UserRepository;
import com.webwizards.screenseekers.security.jwt.JwtUtils;
import com.webwizards.screenseekers.security.services.UserDetailsImpl;
import com.webwizards.screenseekers.utils.ResponseMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="http://localhost:8081")
public class AuthController {
	  @Autowired
	  AuthenticationManager authenticationManager;

	  @Autowired
	  UserRepository userRepository;

	  @Autowired
	  RoleRepository roleRepository;

	  @Autowired
	  PasswordEncoder encoder;

	  @Autowired
	  JwtUtils jwtUtils;

	  @PostMapping("/signin")
	  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

	    //validate if the user exists and it's disabled
		Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
		
		if (!user.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			
			User userObj = user.get();
			
			if (userObj.getDeletedAt() == null) {
				Authentication authentication = authenticationManager.authenticate(
				        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

				    SecurityContextHolder.getContext().setAuthentication(authentication);
				    String jwt = jwtUtils.generateJwtToken(authentication);
				    
				    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
				    List<String> roles = userDetails.getAuthorities().stream()
				        .map(item -> item.getAuthority())
				        .collect(Collectors.toList());

				    return ResponseEntity.ok(new JwtResponse(jwt, 
				                         userDetails.getId(), 
				                         userDetails.getUsername(), 
				                         userDetails.getEmail(), 
				                         roles));
			}else {
				return new ResponseEntity<>(new ResponseMessage("Username: "+loginRequest.getUsername()+" does not exist"),HttpStatus.NOT_FOUND);
			}
				
		}
		  
		
	  }

	  @PostMapping("/signup")
	  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
	    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
	    	
	    	//if the user exists, let's see if it's disabled
	    	User user = userRepository.findByUsername(signUpRequest.getUsername()).get();
	    	
	    	if (user.getDeletedAt() == null) {
	    		//it's active!
	    		return ResponseEntity
	    		          .badRequest()
	    		          .body(new MessageResponse("Error: Username is already taken!"));
	    	}else {
	    		
	    		//it's inactive, let's re-activate it
	    		user.setEmail(signUpRequest.getEmail());
	    		user.setPassword(encoder.encode(signUpRequest.getPassword()));
	    		user.setUpdatedAt(new Date());
	    		user.setDeletedAt(null);
	    		
	    		userRepository.save(user);
	    		
	    		return ResponseEntity
	    		          .ok()
	    		          .body(new MessageResponse("User re-activated successfully"));
	    		
	    	}
	      
	    }

	    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
	      return ResponseEntity
	          .badRequest()
	          .body(new MessageResponse("Error: Email is already in use!"));
	    }

	    // Create new user's account
	    User user = new User(signUpRequest.getUsername(), 
	               signUpRequest.getEmail(),
	               encoder.encode(signUpRequest.getPassword()));
	    
	    user.setCreatedAt(new Date());

	    Set<String> strRoles = signUpRequest.getRole();
	    Set<Role> roles = new HashSet<>();

	    if (strRoles == null) {
	      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
	          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	      roles.add(userRole);
	    } else {
	      strRoles.forEach(role -> {
	        switch (role) {
	        case "admin":
	          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	          roles.add(adminRole);

	          break;
	          
	        default:
	          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	          roles.add(userRole);
	        }
	      });
	    }

	    user.setRoles(roles);
	    userRepository.save(user);

	    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	  }
}
