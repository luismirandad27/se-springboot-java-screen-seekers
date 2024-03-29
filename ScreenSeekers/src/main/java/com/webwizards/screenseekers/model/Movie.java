/**
 * Class File: Rating.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the information of the different Rating
 * v1.01: Added constructor for rating and comment.
 * 
 * @author Victor Chawsukho, Regal Cruz
 * @version 1.01
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
import jakarta.persistence.Lob;
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
	private Date releaseDate;
	@Column(name="length")
	private int length;
	@Column(name="synopsis", length=500)
	private String synopsis;
	@Column(name="classificationRating")
	private String classificationRating;
	@Column(name="movieTrailerLink")
	private String movieTrailerLink;
	@Column(name="isInTheaters")
	private Boolean isInTheaters;
	@Column(name="isInStreaming")
	private Boolean isInStreaming;
	@Column(name="isComingSoon")
	private Boolean isComingSoon;
	@Column(name= "posterImage")
	private String posterImage;
	@Column(name= "trailerImage")
	private String trailerImage;
	@Column(name="whereToWatch")
	private String[] whereToWatch;
	@Column(name="createdAt")
	private Date createdAt;
	@Column(name="updatedAt")
	private Date updatedAt;
	
	//Setting relation with Rating table (comes from a Many to Many relationship)
	@OneToMany(mappedBy = "movie",
				cascade = CascadeType.ALL,
				fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Rating> ratings = new HashSet<>();
	
	//Setting relation with Watchlist table (comes from a Many to Many relationship)
	@OneToMany(mappedBy="movie",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<WatchlistDetail> watchlistDetails = new HashSet<>(); 
	
	//Setting relation with Crew table (comes from a Many to Many relationship)
	@OneToMany(mappedBy="movie",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<ProductionCrew> productionCrews = new HashSet<>(); 
	
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
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
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
	public Boolean getIsInTheaters() {
		return isInTheaters;
	}
	public void setIsInTheaters(Boolean isInTheaters) {
		this.isInTheaters = isInTheaters;
	}
	public Boolean getIsInStreaming() {
		return isInStreaming;
	}
	public void setIsInStreaming(Boolean isInStreaming) {
		this.isInStreaming = isInStreaming;
	}
	
	public Boolean getIsComingSoon() {
		return isComingSoon;
	}
	public void setIsComingSoon(Boolean isComingSoon) {
		this.isComingSoon = isComingSoon;
	}
	public String getPosterImage() {
		return posterImage;
	}
	public void setPosterImage(String posterImage) {
		this.posterImage = posterImage;
	}
	public String getTrailerImage() {
		return trailerImage;
	}
	public void setTrailerImage(String trailerImage) {
		this.trailerImage = trailerImage;
	}
	
	public String[] getWhereToWatch() {
		return whereToWatch;
	}
	public void setWhereToWatch(String[] whereToWatch) {
		this.whereToWatch = whereToWatch;
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
	public Set<Rating> getRatings() {
		return ratings;
	}
	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}
	public Set<WatchlistDetail> getWatchlistDetails() {
		return watchlistDetails;
	}
	public void setWatchlistDetails(Set<WatchlistDetail> watchlistDetails) {
		this.watchlistDetails = watchlistDetails;
	}
	
	
	public Set<ProductionCrew> getProductionCrews() {
		return productionCrews;
	}
	public void setProductionCrews(Set<ProductionCrew> productionCrews) {
		this.productionCrews = productionCrews;
	}
	public Movie(String title, String genre, Date releaseDate, int length, String synopsis, String classificationRating,
			String movieTrailerLink, Boolean isInTheaters, Boolean isInStreaming, Boolean isComingSoon, String[] whereToWatch) {
		
		this.title = title;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.length = length;
		this.synopsis = synopsis;
		this.classificationRating = classificationRating;
		this.movieTrailerLink = movieTrailerLink;
		this.createdAt = new Date();
		this.isInTheaters = isInTheaters;
		this.isInStreaming = isInStreaming;
		this.isComingSoon = isComingSoon;
		this.whereToWatch = whereToWatch;
	}
	
	public Movie(String title, String genre, Date releaseDate, int length, String synopsis, String classificationRating,
			String movieTrailerLink, Boolean isInTheaters,Boolean isInStreaming, Boolean isComingSoon,String[] whereToWatch, 
			String posterImage, String trailerImage) {
		
		this.title = title;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.length = length;
		this.synopsis = synopsis;
		this.classificationRating = classificationRating;
		this.movieTrailerLink = movieTrailerLink;
		this.createdAt = new Date();
		this.isInTheaters = isInTheaters;
		this.isInStreaming = isInStreaming;
		this.isComingSoon = isComingSoon;
		this.whereToWatch = whereToWatch;
		this.posterImage = posterImage;
		this.trailerImage = trailerImage;
	}
	
	public Movie() {
		
		// TODO Auto-generated constructor stub
	}
	
	
}