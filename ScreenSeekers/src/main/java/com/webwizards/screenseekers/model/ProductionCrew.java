package com.webwizards.screenseekers.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
@Table(name="productionCrew")
@JsonSerialize(using = ProdCrewSerializer.class)
public class ProductionCrew {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="moveiRole")
	private String movieRole;
	
	@Column(name="characterName")
	private String characterName;
	
	@Column(name="createdAt")
	private Date createdAt;
	
	@Column(name="updatedAt")
	private Date updatedAt;
	
	@Column(name="deletedAt")
	private Date deletedAt;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "movieId", nullable = false)
	private Movie movie;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "crewId", nullable = false)
	private Crew crew;
	
	

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Crew getCrew() {
		return crew;
	}

	public void setCrew(Crew crew) {
		this.crew = crew;
	}

	public ProductionCrew() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductionCrew(String movieRole, String characterName, Date createdAt, Date updatedAt, Date deletedAt) {
		super();
		this.movieRole = movieRole;
		this.characterName = characterName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMovieRole() {
		return movieRole;
	}

	public void setMovieRole(String movieRole) {
		this.movieRole = movieRole;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
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
	

	
	

}
