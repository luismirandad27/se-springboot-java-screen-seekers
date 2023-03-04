/**
 * Class File: CrewRepository.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the Jpa methods to access the Crew table from
 * the database.
 *
 * 
 * @Author Regal Cruz
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webwizards.screenseekers.model.CrewMember;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
	List<CrewMember> findByFirstName(String firstName);
}
