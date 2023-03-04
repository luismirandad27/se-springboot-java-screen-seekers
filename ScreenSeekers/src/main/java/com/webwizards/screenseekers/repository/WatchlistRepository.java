/**
 * Class File: WatchlistRepository.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the Jpa methods to access the Watchlist table from
 * the database
 * 
 * @author Luis Miguel Miranda, Marc Deroca
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.webwizards.screenseekers.model.Watchlist;

@Transactional
public interface WatchlistRepository extends JpaRepository<Watchlist,Long> {
	
	List<Watchlist> findByName(String name);
	
	List<Watchlist> findByUserId(Long id);
	
	@Modifying
	@Query("DELETE FROM Watchlist w WHERE w.user.id = ?1")
	void deleteByUserId(Long id);
	  
}
