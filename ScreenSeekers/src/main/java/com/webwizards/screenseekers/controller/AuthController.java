/*
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
 */

package com.webwizards.screenseekers.controller;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
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
