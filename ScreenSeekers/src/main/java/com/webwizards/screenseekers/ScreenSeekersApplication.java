/**
 * Class File: ScreenSeekersApplication.java
 * 
 * ------------
 * Description:
 * ------------
 * v1.01: added 20 movies, Rating Repo (testing purposes)
 * v1.02: added 6 users, User Repo (testing purposes)
 * v1.03: added crew member, Crew Repo (testing purposes)
 * 
 * @author Victor Chawsukho, Luis Miguel Miranda, Regal Cruz
 * @version 1.01
 * 
 **/

package com.webwizards.screenseekers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.webwizards.screenseekers.model.Crew;
import com.webwizards.screenseekers.model.ERole;
import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.model.ProductionCrew;
import com.webwizards.screenseekers.model.Role;
import com.webwizards.screenseekers.model.User;
import com.webwizards.screenseekers.repository.CrewRepository;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.ProductionCrewRepository;
import com.webwizards.screenseekers.repository.RatingRepository;
import com.webwizards.screenseekers.repository.RoleRepository;
import com.webwizards.screenseekers.repository.UserRepository;

@SpringBootApplication
public class ScreenSeekersApplication {
	// this is for parsing input date from ApplicationRunner
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) {
		SpringApplication.run(ScreenSeekersApplication.class, args);
	}

	@Bean
	ApplicationRunner init(RoleRepository roleRepo, MovieRepository movieRepo, RatingRepository ratingRepo, UserRepository userRepo, CrewRepository crewRepo, ProductionCrewRepository prodCrewRepo) {

		return args -> {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			movieRepo.save(new Movie("The Dark Knight", "Action, Crime, Drama", dateFormat.parse("2008-07-18"), 152,
					"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
					"PG-13", "https://www.youtube.com/watch?v=EXeTwQWrcwY",false));
			movieRepo.save(new Movie("Inception", "Action, Adventure, Sci-Fi", dateFormat.parse("2010-07-16"), 148,
					"A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
					"PG-13", "https://www.youtube.com/watch?v=YoHD9XEInc0",false));
			movieRepo.save(new Movie("The Lord of the Rings: The Return of the King", "Adventure, Drama, Fantasy",
					dateFormat.parse("2003-12-17"), 201,
					"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.",
					"PG-13", "https://www.youtube.com/watch?v=r5X-hFf6Bwo",false));
			movieRepo.save(new Movie("The Matrix", "Action, Sci-Fi", dateFormat.parse("1999-03-31"), 136,
					"A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
					"R", "https://www.youtube.com/watch?v=m8e-FF8MsqU",false));
			movieRepo.save(new Movie("Interstellar", "Adventure, Drama, Sci-Fi", dateFormat.parse("2014-11-07"), 169,
					"A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
					"PG-13", "https://www.youtube.com/watch?v=zSWdZVtXT7E",false));
			movieRepo.save(new Movie("The Silence of the Lambs", "Crime, Drama, Thriller",
					dateFormat.parse("1991-02-14"), 118,
					"A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.",
					"R", "https://www.youtube.com/watch?v=RuX2MQeb8UM",false));
			
			movieRepo.save(new Movie("Avengers: Endgame", "Action, Adventure, Drama", dateFormat.parse("2019-04-26"),
					181,
					"After the devastating events of Avengers: Infinity War (2018), the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to undo Thanos' actions and restore order to the universe.",
					"PG-13", "https://www.youtube.com/watch?v=TcMBFSGVi1c",false));
			movieRepo.save(new Movie("The Shawshank Redemption", "Drama", dateFormat.parse("1994-10-14"), 142,
					"Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
					"R", "https://www.youtube.com/watch?v=6hB3S9bIaco",false));

			movieRepo.save(new Movie("Pulp Fiction", "Crime, Drama", dateFormat.parse("1994-10-14"), 154,
					"The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
					"R", "https://www.youtube.com/watch?v=s7EdQ4FqbhY",false));
			movieRepo.save(new Movie("La La Land", "Comedy, Drama, Music", dateFormat.parse("2016-12-09"), 128,
					"While navigating their careers in Los Angeles, a pianist and an actress fall in love while attempting to reconcile their aspirations for the future.",
					"PG-13", "https://www.youtube.com/watch?v=0pdqf4P9MB8",false));

			movieRepo.save(new Movie("Inside Out", "Animation, Adventure, Comedy", dateFormat.parse("2015-06-19"), 95,
					"After young Riley is uprooted from her Midwest life and moved to San Francisco, her emotions - Joy, Fear, Anger, Disgust and Sadness - conflict on how best to navigate a new city, house, and school.",
					"PG", "https://www.youtube.com/watch?v=yRUAzGQ3nSY",false));

			movieRepo.save(new Movie("The Hunger Games", "Action, Adventure, Sci-Fi", dateFormat.parse("2012-03-23"),
					142,
					"Katniss Everdeen voluntarily takes her younger sister's place in the Hunger Games: a televised competition in which two teenagers from each of the twelve Districts of Panem are chosen at random to fight to the death.",
					"PG-13", "https://www.youtube.com/watch?v=mfmrPu43DF8",false));

			movieRepo.save(new Movie("Toy Story 3", "Animation, Adventure, Comedy", dateFormat.parse("2010-06-18"), 103,
					"The toys are mistakenly delivered to a day-care center instead of the attic right before Andy leaves for college, and it's up to Woody to convince the other toys that they weren't abandoned and to return home.",
					"G", "https://www.youtube.com/watch?v=JcpWXaA2qeg",false));

			movieRepo.save(new Movie("Black Panther", "Action, Adventure, Sci-Fi", dateFormat.parse("2018-02-16"), 134,
					"T'Challa, heir to the hidden but advanced kingdom of Wakanda, must step forward to lead his people into a new future and confront a challenger from his country's past.",
					"PG-13", "https://www.youtube.com/watch?v=xjDjIWPwcPU",false));

			movieRepo.save(new Movie("Coco", "Animation, Adventure, Family", dateFormat.parse("2017-11-22"), 105,
					"Aspiring musician Miguel, confronted with his family's ancestral ban on music, enters the Land of the Dead to find his great-great-grandfather, a legendary singer.",
					"PG", "https://www.youtube.com/watch?v=xlnPHQ3TLX8",false));
			movieRepo.save(new Movie("The Imitation Game", "Biography, Drama, Thriller", dateFormat.parse("2014-11-28"),
					114,
					"During World War II, the English mathematical genius Alan Turing tries to crack the German Enigma code with help from fellow mathematicians.",
					"PG-13", "https://www.youtube.com/watch?v=S5CjKEFb-sM",false));

			movieRepo.save(new Movie("Mad Max: Fury Road", "Action, Adventure, Sci-Fi", dateFormat.parse("2015-05-15"),
					120,
					"In a post-apocalyptic wasteland, a woman rebels against a tyrannical ruler in search for her homeland with the aid of a group of female prisoners, a psychotic worshiper, and a drifter named Max.",
					"R", "https://www.youtube.com/watch?v=hEJnMQG9ev8",false));
			movieRepo.save(new Movie("Crazy Rich Asians", "Comedy, Drama, Romance", dateFormat.parse("2018-08-15"), 120,
					"This contemporary romantic comedy, based on a global bestseller, follows native New Yorker Rachel Chu to Singapore to meet her boyfriend's family.",
					"PG-13", "https://www.youtube.com/watch?v=ZQ-YX-5bAs0",false));

			movieRepo.save(new Movie("Spider-Man: Into the Spider-Verse", "Animation, Action, Adventure",
					dateFormat.parse("2018-12-14"), 117,
					"Teen Miles Morales becomes Spider-Man of his reality, crossing his path with five counterparts from other dimensions to stop a threat for all realities.",
					"PG", "https://www.youtube.com/watch?v=tg52up16eq0",false));
			movieRepo.save(new Movie("The Grand Budapest Hotel", "Comedy, Drama", dateFormat.parse("2014-03-28"), 100,
					"The adventures of Gustave H, a legendary concierge at a famous hotel from the fictional Republic of Zubrowka between the first and second World Wars, and Zero Moustafa, the lobby boy who becomes his most trusted friend.",
					"R", "https://www.youtube.com/watch?v=1Fg5iWmQjwk",false));
			movieRepo.save(new Movie("Titanic", "Drama", dateFormat.parse("1997-12-19"), 180, "Synopsys here..", "R",
					"https://www.youtube.com/watch?v=I7c1etV7D7g",false));
			

			roleRepo.save(new Role(ERole.ROLE_ADMIN));
			roleRepo.save(new Role(ERole.ROLE_USER));

			
			//Users
			User user1 = new User("lmirandad","lmirandad27@gmail.com","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu");
			User user2 = new User("ftoffanelli0","rchatel0@merriam-webster.com","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu");
			User user3 = new User("dmiddas1","adinsmore1@engadget.com","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu");
			User user4 = new User("wzelley2","patlee2@hibu.com","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu");
			User user5 = new User("cstandishbrooks3","lbellay3@sciencedirect.com","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu");
			User user6 = new User("efoucar4","bfarthin4@oaic.gov.au","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu");
			User user7 = new User("bpyer5","kfoffano5@arizona.edu","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu");
			User user8 = new User("admin123","admin@screenseekers.com","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu");

			Set<Role> roles = new HashSet<>();
			Role userRole = roleRepo.findByName(ERole.ROLE_USER)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(userRole);
		          
		    Set<Role> rolesAdmin = new HashSet<>();
			Role adminRole = roleRepo.findByName(ERole.ROLE_ADMIN)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			rolesAdmin.add(adminRole);
			
			user1.setRoles(roles);
			user2.setRoles(roles);
			user3.setRoles(roles);
			user4.setRoles(roles);
			user5.setRoles(roles);
			user6.setRoles(roles);
			user7.setRoles(roles);
			user8.setRoles(rolesAdmin);
			
			userRepo.save(user1);
			userRepo.save(user2);
			userRepo.save(user3);
			userRepo.save(user4);
			userRepo.save(user5);
			userRepo.save(user6);
			userRepo.save(user7);
			userRepo.save(user8);
			
			//creating crew object for testing
			Crew crew1 = new Crew("Pedro", "Pascal",dateFormat.parse("2018-08-15"), "Phil", "Award");
			Crew crew2 = new Crew("Bella", "Ramsey",dateFormat.parse("2018-08-15"), "Phil", "Award");
			
			crewRepo.save(crew1);
			crewRepo.save(crew2);
			
			//creating prodcrew objects for testing
			ProductionCrew prodCrew1 = new ProductionCrew("Man1");
			ProductionCrew prodCrew2 = new ProductionCrew("Man2");
			ProductionCrew prodCrew3 = new ProductionCrew("Man3");
			ProductionCrew prodCrew4 = new ProductionCrew("Man4");
			
			//linking the 3 objects to demonstrate many to many relationship
			crew1.setProductionCrews(new HashSet<>(Arrays.asList(prodCrew1, prodCrew2))); 
			crew2.setProductionCrews(new HashSet<>(Arrays.asList(prodCrew1, prodCrew2)));
			
			long id1, id2;
			id1 = 1;
			id2 = 2;
			
			Movie movie1 = movieRepo.findById(id1).get();
			Movie movie2 = movieRepo.findById(id2).get();
			
			movie1.setProductionCrews(new HashSet<>(Arrays.asList(prodCrew1, prodCrew2)));
			movie2.setProductionCrews(new HashSet<>(Arrays.asList(prodCrew1, prodCrew2)));
			
			prodCrew1.setCrew(crew1);
			prodCrew1.setMovie(movie1);
			
			prodCrew2.setCrew(crew2);
			prodCrew2.setMovie(movie2);
			
			prodCrew3.setCrew(crew1);
			prodCrew3.setMovie(movie2);
			
			prodCrew4.setCrew(crew2);
			prodCrew4.setMovie(movie1);
			
			//saving crew and movie objects back to the DB
			crewRepo.save(crew1);crewRepo.save(crew2);
			movieRepo.save(movie1);movieRepo.save(movie2);
			  
		};

	}
	
	

}