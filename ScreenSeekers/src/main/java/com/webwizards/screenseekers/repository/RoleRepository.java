package com.webwizards.screenseekers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webwizards.screenseekers.model.ERole;
import com.webwizards.screenseekers.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{
	Optional<Role> findByName(ERole name);
}
