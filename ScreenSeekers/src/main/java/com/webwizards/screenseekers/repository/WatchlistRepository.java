package com.webwizards.screenseekers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webwizards.screenseekers.model.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist,Long> {

}
