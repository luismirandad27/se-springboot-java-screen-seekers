/*
 * Class File: UserRepository.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the Jpa methods to access the User table from
 * the database
 * 
 * @author Luis Miguel Miranda
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.webwizards.screenseekers.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
}
