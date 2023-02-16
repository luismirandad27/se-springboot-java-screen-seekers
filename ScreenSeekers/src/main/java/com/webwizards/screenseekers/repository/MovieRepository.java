package com.webwizards.screenseekers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webwizards.screenseekers.model.Movie;

public interface MovieRepository extends JpaRepository <Movie, Long>{
/*
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.details
 * interface PersonRepository extends Repository<Person, Long> {

  List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);

  // Enables the distinct flag for the query
  List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
  List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);

  // Enabling ignoring case for an individual property
  List<Person> findByLastnameIgnoreCase(String lastname);
  // Enabling ignoring case for all suitable properties
  List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);

  // Enabling static ORDER BY for a query
  List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
  List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
}
 */
	
	
	//	List<Movie> findByTitle(String title);
	
	//Search (Victor)
	List<Movie> findByTitleContainingIgnoreCase(String title);
	List<Movie> findByGenreIgnoreCase(String genre);
//	List<Movie> findByReleaseDate(String releaseDate);
//	List<Movie> findByLength(int length);
//	List<Movie> findBySynopsisIgnoreCase(String synopsis);
	
	/* private Long id;
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
	private String classificationRating; */
}
