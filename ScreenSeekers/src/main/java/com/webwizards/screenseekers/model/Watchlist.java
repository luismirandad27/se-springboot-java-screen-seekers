/**
 * Class File: Watchlist.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the information of the watchlist created by
 * a user.
 * 
 * @author Luis Miguel Miranda
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="watchlist")
public class Watchlist {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="createdAt")
	private Date createdAt;
	
	@Column(name="updatedAt")
	private Date updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name="userId", nullable = false)
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy="watchlist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<WatchlistDetail> watchlistDetails = new HashSet<>();

	public Watchlist(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.createdAt =  new Date();
	}
	
	public Watchlist() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<WatchlistDetail> getWatchlistDetails() {
		return watchlistDetails;
	}

	public void setWatchlistDetails(Set<WatchlistDetail> watchlistDetails) {
		this.watchlistDetails = watchlistDetails;
	}
	
}
