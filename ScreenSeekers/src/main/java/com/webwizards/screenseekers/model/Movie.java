package com.webwizards.screenseekers.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="movie")
public class Movie {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="title")
	private String title;
	@Column(name="genre")
	private String genre;
	@Column(name="releaseDate")
	private String releaseDate;
	@Column(name="length")
	private int length;
	@Column(name="synopsis")
	private String synopsis;
	@Column(name="classificationRating")
	private String classificationRating;
	@Column(name="movieTrailerLink")
	private String movieTrailerLink;
	@Column(name="createdAt")
	private String createdAt;
	@Column(name="updatedAt")
	private String updatedAt;
	@Column(name="deletedAt")
	private String deletedAt;
	
	//Setting relation with Rating table (comes from a Many to Many relationship)
	@OneToMany(mappedBy = "movie",
				cascade = CascadeType.ALL,
				fetch = FetchType.LAZY)
	private Set<Rating> ratings = new HashSet<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getClassificationRating() {
		return classificationRating;
	}
	public void setClassificationRating(String classificationRating) {
		this.classificationRating = classificationRating;
	}
	public String getMovieTrailerLink() {
		return movieTrailerLink;
	}
	public void setMovieTrailerLink(String movieTrailerLink) {
		this.movieTrailerLink = movieTrailerLink;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(String deletedAt) {
		this.deletedAt = deletedAt;
	}
	public Set<Rating> getRatings() {
		return ratings;
	}
	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}
	public Movie(String title, String genre, String releaseDate, int length, String synopsis, String classificationRating,
			String movieTrailerLink, String createdAt, String updatedAt, String deletedAt) {
		
		this.title = title;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.length = length;
		this.synopsis = synopsis;
		this.classificationRating = classificationRating;
		this.movieTrailerLink = movieTrailerLink;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}
	public Movie() {
		
		// TODO Auto-generated constructor stub
	}
	
	
}