/*
 * Class File: WatchlistRepository.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the Jpa methods to access the Watchlist table from
 * the database
 * 
 * @author Luis Miguel Miranda
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webwizards.screenseekers.model.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist,Long> {
	List<Watchlist> findByName(String name);
}
