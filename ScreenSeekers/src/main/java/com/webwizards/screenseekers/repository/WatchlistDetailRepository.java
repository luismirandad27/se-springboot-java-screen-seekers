/*
 * Class File: WatchlistDetailRepository.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the Jpa methods to access the WatchlistDetail table from
 * the database
 * 
 * @author Luis Miguel Miranda
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webwizards.screenseekers.model.WatchlistDetail;

public interface WatchlistDetailRepository extends JpaRepository<WatchlistDetail,Long>{
	Optional<WatchlistDetail> findById(Long id);
 
} 
