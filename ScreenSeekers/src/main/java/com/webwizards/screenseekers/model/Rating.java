/*
 * Class File: Rating.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the information of the different Rating
 * v1.01: Added constructor for rating and comment.
 * @author Victor Chawsukho
 * @version 1.01
 * 
 */

package com.webwizards.screenseekers.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


@Entity
@Table(name="rating")
@JsonSerialize(using = RatingSerializer.class)
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="userRating")
	private int userRating;
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="createdAt")
	private Date createdAt;
	
	@Column(name="updatedAt")
	private Date updatedAt;
	
	@Column(name="deletedAt")
	private Date deletedAt;
	
	//Setting relation with User table (comes from a Many to Many relationship)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "userId", nullable=false)
	@JsonIgnore
	private User user;
	
	//Setting relation with Movie table (comes from a Many to Many relationship)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "movieId", nullable=false)
	@JsonIgnore
	private Movie movie;
	
	public Rating() {
		
	}

	
	//userId and movieId are FK and not allow null, how to fetch when Post new Rating and Comment?
	public Rating(int userRating, String comment, Date createdAt, Date updatedAt, Date deletedAt, User user, Movie movie) {
		this.userRating = userRating;
		this.comment = comment;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
		this.user = user;
		this.movie = movie;
	}
	
	public Rating(int userRating, String comment) {
		this.userRating = userRating;
		this.comment = comment;
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

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
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
