/*
 * Class File: RoleRepository.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the Jpa methods to access the Role table from
 * the database
 * 
 * @author Luis Miguel Miranda
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webwizards.screenseekers.model.ERole;
import com.webwizards.screenseekers.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{
	Optional<Role> findByName(ERole name);
}
