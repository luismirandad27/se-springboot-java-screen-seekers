/**
 * Class File: RatingController.java
 * 
 * ------------
 * Description:
 * ------------
 * This class will store the API methods with regards of the movies information management.
 * That includes:
 * 
 * 1) Retrieve all movies accessed by Admin
 * 2) Retrieve random movies  
 * 3) Get movies based on the Title
 * 4) Get movies based on the Genre
 * 5) Get movies based on the ReleaseDate 
 * 6) Get movies based on the id
 * 7) Create movies
 * 8) Update the movie information
 * 9) Delete movies based on id
 * 10) Delete all movies
 * 11) Get recommendations for an specific user
 * 12) Add a crew member to the movie's crew (1 crew member - an actor for example - can work in multiple movies)
 * 
 * @author Victor Chawsukho, Luis Miguel Miranda
 * @version 1.02
 * 
 **/

package com.webwizards.screenseekers.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webwizards.screenseekers.model.Crew;
import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.model.ProductionCrew;
import com.webwizards.screenseekers.model.User;
import com.webwizards.screenseekers.repository.CrewRepository;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.ProductionCrewRepository;
import com.webwizards.screenseekers.repository.UserRepository;
import com.webwizards.screenseekers.utils.Recommender;
import com.webwizards.screenseekers.utils.ResponseMessage;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class MovieController {

	@Autowired
	MovieRepository movieRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CrewRepository crewRepo; 
	
	@Autowired
	ProductionCrewRepository productionCrewRepo;
	

	@GetMapping("/movies")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required = false) String title, 
													@RequestParam(required = false) String genre, 
													@RequestParam(required = false) Integer year) {
		try {
			
			List<Movie> myList = new ArrayList<Movie>();
			
			if (title != null) {
				
				myList = movieRepo.findByTitleContainingIgnoreCase(title);
				
			} else if(genre != null) {
				
				myList = movieRepo.findByGenreContainingIgnoreCase(genre);
				
			} else if(year != null) {
				
				myList = movieRepo.findByReleaseDateYear(year);
				
			}
			else {
				
				myList = movieRepo.findAll();
				
			}
			return new ResponseEntity<>(myList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping("/movies/random")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Movie>> getRandomMovies(@RequestParam(required = false) String title) {
		try {
			List<Movie> myList = new ArrayList<Movie>();
			if (title == null) {
				myList = movieRepo.findRandom();
			} else {
				myList = movieRepo.findByTitleContainingIgnoreCase(title);
			}

			return new ResponseEntity<>(myList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/movies/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Movie> getMovie(@PathVariable("id") Long id) {
		try {
			if (movieRepo.findById(id).isPresent()) {
				return new ResponseEntity<>(movieRepo.findById(id).get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/movies")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
		try {
			Movie myMovie = movie;

			movieRepo.save(new Movie(movie.getTitle(), 
									 movie.getGenre(), 
									 movie.getReleaseDate(), 
									 movie.getLength(), 
									 movie.getSynopsis(), 
									 movie.getClassificationRating(), 
									 movie.getMovieTrailerLink(), 
									 movie.getIsInTheaters()));

			return new ResponseEntity<>(myMovie, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("movies/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie, @PathVariable Long id) {
		try {
			Optional<Movie> myMovie = movieRepo.findById(id);
			if (movieRepo.findById(id).isPresent()) {
				
				Movie _myMovie = myMovie.get();
				
				_myMovie.setTitle(movie.getTitle());
				_myMovie.setGenre(movie.getGenre());
				_myMovie.setReleaseDate(movie.getReleaseDate());
				_myMovie.setLength(movie.getLength());
				_myMovie.setSynopsis(movie.getSynopsis());
				_myMovie.setClassificationRating(movie.getClassificationRating());
				_myMovie.setMovieTrailerLink(movie.getMovieTrailerLink());
				_myMovie.setIsInTheaters(movie.getIsInTheaters());
				_myMovie.setUpdatedAt(new Date());
				
				movieRepo.save(_myMovie);
				
				return new ResponseEntity<>(_myMovie, HttpStatus.OK);
			} else
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("movies/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseMessage> deleteMovie(@PathVariable Long id) {
		try {
			
			Optional<Movie> movie = movieRepo.findById(id);
			
			if (movie.isPresent()) {
				
				String movieName = movie.get().getTitle();
				
				movieRepo.deleteById(id);
				
				String message = "Movie: "+movieName+" has been deleted successfully!";
				
				return new ResponseEntity<>(new ResponseMessage(message),HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
				
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/movies")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseMessage> deleteAllMovies() {
		try {
			
			movieRepo.deleteAll();
			
			String message = "All movies have been deleted successfully!";
			
			return new ResponseEntity<>(new ResponseMessage(message),HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/movies/{id}/recommend")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Movie>> recommendMoviesToUser(@PathVariable long id){
		
		try {
			
			//Getting All Movies
			List<Movie> allMovies = movieRepo.findAll();
			
			//Getting All Available Ratings
			List<User> allUsers = userRepo.findAllUsersAvailable();
			
			//Get the User Info
			Optional<User> user = userRepo.findById(id);
			
			if (allMovies.isEmpty() || !user.isPresent() || allUsers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			Recommender movieRecommender = new Recommender();
			
			//Let's create the HashMap
			movieRecommender.setRatings(allUsers);
			
			List<Movie> recommendations = movieRecommender.getRecommendedMovieList(allMovies, id);
			
			if (recommendations.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(recommendations,HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/movies/{movieId}/add-crew-member/{crewId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductionCrew> addCrewMemberToMovie(@PathVariable long movieId, @PathVariable long crewId,@RequestBody ProductionCrew productionCrew){
		
		try {
			
			Optional<Movie> movie = movieRepo.findById(movieId);
			Optional<Crew> crew = crewRepo.findById(crewId);
			
			if (!movie.isPresent() || !crew.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			ProductionCrew prodCrew = new ProductionCrew(
					productionCrew.getMovieRole(),
					productionCrew.getCharacterName());
			
			prodCrew.setMovie(movie.get());
			prodCrew.setCrew(crew.get());
			
			productionCrewRepo.save(prodCrew);
			
			return new ResponseEntity<>(prodCrew,HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}