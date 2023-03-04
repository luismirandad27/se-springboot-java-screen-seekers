/**
 * Class File: Rating.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the information of the different Rating
 * v1.01: Added constructor for rating and comment.
 * 
 * @author Victor Chawsukho
 * @version 1.01
 * 
 */

package com.webwizards.screenseekers.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


//@JsonSerialize(using = RatingSerializer.class)
@Entity
@Table(name="rating")
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="userRating")
	private int userRating;
	
	@Column(name="comment", length=500)
	private String comment;
	
	@Column(name="createdAt")
	private Date createdAt;
	
	@Column(name="updatedAt")
	private Date updatedAt;
	
	//Setting relation with User table (comes from a Many to Many relationship)
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "userId", nullable=false)
	@JsonIgnore
	@JsonProperty("user")
	private User user;
	
	//Setting relation with Movie table (comes from a Many to Many relationship)
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "movieId", nullable=false)
	@JsonIgnore
	@JsonProperty("movie")	
	private Movie movie;
	
	public Rating() {
		
	}

	//userId and movieId are FK and not allow null, how to fetch when Post new Rating and Comment?
	public Rating(int userRating, String comment) {
		this.userRating = userRating;
		this.comment = comment;
		this.createdAt = new Date();
		this.user = null;
		this.movie = null;
		this.updatedAt = null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getUserRating() {
		return userRating;
	}

	public void setUserRating(int userRating) {
		this.userRating = userRating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	
	
}
