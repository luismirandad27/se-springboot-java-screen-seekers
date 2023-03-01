/**
 * Class File: MovieRepository.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the Jpa methods to access the ProductionCrew table from
 * the database.
 *
 * 
 	Regal Cruz
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webwizards.screenseekers.model.ProductionCrew;

public interface ProductionCrewRepository extends JpaRepository<ProductionCrew, Long> {

}
