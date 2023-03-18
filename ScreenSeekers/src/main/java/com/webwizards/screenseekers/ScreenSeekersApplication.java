/**
 * Class File: ScreenSeekersApplication.java
 * 
 * ------------
 * Description:
 * ------------
 * added 20 movies, Rating Repo (testing purposes)
 * added 8 users, User Repo (testing purposes)
 * 
 * @author Victor Chawsukho, Luis Miguel Miranda, Regal Cruz
 * @version 1.0
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

import com.webwizards.screenseekers.model.CrewMember;
import com.webwizards.screenseekers.model.ERole;
import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.model.ProductionCrew;
import com.webwizards.screenseekers.model.Rating;
import com.webwizards.screenseekers.model.Role;
import com.webwizards.screenseekers.model.User;
import com.webwizards.screenseekers.model.Watchlist;
import com.webwizards.screenseekers.model.WatchlistDetail;
import com.webwizards.screenseekers.repository.CrewMemberRepository;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.ProductionCrewRepository;
import com.webwizards.screenseekers.repository.RatingRepository;
import com.webwizards.screenseekers.repository.RoleRepository;
import com.webwizards.screenseekers.repository.UserRepository;
import com.webwizards.screenseekers.repository.WatchlistDetailRepository;
import com.webwizards.screenseekers.repository.WatchlistRepository;

@SpringBootApplication
public class ScreenSeekersApplication {
	// this is for parsing input date from ApplicationRunner
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final static String POSTER_IMAGE = "poster-image.jpg";
	private final static String TRAILER_IMAGE = "trailer-image.jpg";

	public static void main(String[] args) {
		SpringApplication.run(ScreenSeekersApplication.class, args);
	}

	@Bean
	ApplicationRunner init(RoleRepository roleRepo, 
						   MovieRepository movieRepo, 
						   RatingRepository ratingRepo, 
						   UserRepository userRepo, 
						   CrewMemberRepository crewRepo, 
						   ProductionCrewRepository prodCrewRepo,
						   WatchlistRepository watchlistRepo,
						   WatchlistDetailRepository watchlistDetailRepo) {

		return args -> {
			
			//MOVIE'S SAMPLES
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			String [] whereToWatch = new String[] {"Netflix","Cinema","PrimeVideo"};
			
			Movie movie1 = new Movie("The Dark Knight", "Action, Crime, Drama", dateFormat.parse("2008-07-18"), 152,
					"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
					"PG-13", "https://www.youtube.com/watch?v=EXeTwQWrcwY",false,whereToWatch, POSTER_IMAGE, TRAILER_IMAGE);
			
			Movie movie2 = new Movie("Inception", "Action, Adventure, Sci-Fi", dateFormat.parse("2010-07-16"), 148,
					"A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
					"PG-13", "https://www.youtube.com/watch?v=YoHD9XEInc0",false,whereToWatch, POSTER_IMAGE, TRAILER_IMAGE);
			
			Movie movie3 = new Movie("The Lord of the Rings: The Return of the King", "Adventure, Drama, Fantasy",
					dateFormat.parse("2003-12-17"), 201,
					"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.",
					"PG-13", "https://www.youtube.com/watch?v=r5X-hFf6Bwo",false,whereToWatch, POSTER_IMAGE, TRAILER_IMAGE);
			
			Movie movie4 = new Movie("The Matrix", "Action, Sci-Fi", dateFormat.parse("1999-03-31"), 136,
					"A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
					"R", "https://www.youtube.com/watch?v=m8e-FF8MsqU",false,whereToWatch, POSTER_IMAGE, TRAILER_IMAGE);
			
			Movie movie5 = new Movie("Interstellar", "Adventure, Drama, Sci-Fi", dateFormat.parse("2014-11-07"), 169,
					"A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
					"PG-13", "https://www.youtube.com/watch?v=zSWdZVtXT7E",false,whereToWatch, POSTER_IMAGE, TRAILER_IMAGE);
			
			Movie movie6 = new Movie("The Silence of the Lambs", "Crime, Drama, Thriller",
					dateFormat.parse("1991-02-14"), 118,
					"A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.",
					"R", "https://www.youtube.com/watch?v=RuX2MQeb8UM",false,whereToWatch, POSTER_IMAGE, TRAILER_IMAGE);
			
			movieRepo.save(movie1);
			movieRepo.save(movie2);
			movieRepo.save(movie3);
			movieRepo.save(movie4);
			movieRepo.save(movie5);
			movieRepo.save(movie6);
			/*
			movieRepo.save(new Movie("Avengers: Endgame", "Action, Adventure, Drama", dateFormat.parse("2019-04-26"),
					181,
					"After the devastating events of Avengers: Infinity War (2018), the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to undo Thanos' actions and restore order to the universe.",
					"PG-13", "https://www.youtube.com/watch?v=TcMBFSGVi1c",false,whereToWatch));
			movieRepo.save(new Movie("The Shawshank Redemption", "Drama", dateFormat.parse("1994-10-14"), 142,
					"Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
					"R", "https://www.youtube.com/watch?v=6hB3S9bIaco",false,whereToWatch));

			movieRepo.save(new Movie("Pulp Fiction", "Crime, Drama", dateFormat.parse("1994-10-14"), 154,
					"The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
					"R", "https://www.youtube.com/watch?v=s7EdQ4FqbhY",false,whereToWatch));
			movieRepo.save(new Movie("La La Land", "Comedy, Drama, Music", dateFormat.parse("2016-12-09"), 128,
					"While navigating their careers in Los Angeles, a pianist and an actress fall in love while attempting to reconcile their aspirations for the future.",
					"PG-13", "https://www.youtube.com/watch?v=0pdqf4P9MB8",false,whereToWatch));

			movieRepo.save(new Movie("Inside Out", "Animation, Adventure, Comedy", dateFormat.parse("2015-06-19"), 95,
					"After young Riley is uprooted from her Midwest life and moved to San Francisco, her emotions - Joy, Fear, Anger, Disgust and Sadness - conflict on how best to navigate a new city, house, and school.",
					"PG", "https://www.youtube.com/watch?v=yRUAzGQ3nSY",false,whereToWatch));

			movieRepo.save(new Movie("The Hunger Games", "Action, Adventure, Sci-Fi", dateFormat.parse("2012-03-23"),
					142,
					"Katniss Everdeen voluntarily takes her younger sister's place in the Hunger Games: a televised competition in which two teenagers from each of the twelve Districts of Panem are chosen at random to fight to the death.",
					"PG-13", "https://www.youtube.com/watch?v=mfmrPu43DF8",false,whereToWatch));

			movieRepo.save(new Movie("Toy Story 3", "Animation, Adventure, Comedy", dateFormat.parse("2010-06-18"), 103,
					"The toys are mistakenly delivered to a day-care center instead of the attic right before Andy leaves for college, and it's up to Woody to convince the other toys that they weren't abandoned and to return home.",
					"G", "https://www.youtube.com/watch?v=JcpWXaA2qeg",false,whereToWatch));

			movieRepo.save(new Movie("Black Panther", "Action, Adventure, Sci-Fi", dateFormat.parse("2018-02-16"), 134,
					"T'Challa, heir to the hidden but advanced kingdom of Wakanda, must step forward to lead his people into a new future and confront a challenger from his country's past.",
					"PG-13", "https://www.youtube.com/watch?v=xjDjIWPwcPU",false,whereToWatch));

			movieRepo.save(new Movie("Coco", "Animation, Adventure, Family", dateFormat.parse("2017-11-22"), 105,
					"Aspiring musician Miguel, confronted with his family's ancestral ban on music, enters the Land of the Dead to find his great-great-grandfather, a legendary singer.",
					"PG", "https://www.youtube.com/watch?v=xlnPHQ3TLX8",false,whereToWatch));
			movieRepo.save(new Movie("The Imitation Game", "Biography, Drama, Thriller", dateFormat.parse("2014-11-28"),
					114,
					"During World War II, the English mathematical genius Alan Turing tries to crack the German Enigma code with help from fellow mathematicians.",
					"PG-13", "https://www.youtube.com/watch?v=S5CjKEFb-sM",false,whereToWatch));

			movieRepo.save(new Movie("Mad Max: Fury Road", "Action, Adventure, Sci-Fi", dateFormat.parse("2015-05-15"),
					120,
					"In a post-apocalyptic wasteland, a woman rebels against a tyrannical ruler in search for her homeland with the aid of a group of female prisoners, a psychotic worshiper, and a drifter named Max.",
					"R", "https://www.youtube.com/watch?v=hEJnMQG9ev8",false,whereToWatch));
			movieRepo.save(new Movie("Crazy Rich Asians", "Comedy, Drama, Romance", dateFormat.parse("2018-08-15"), 120,
					"This contemporary romantic comedy, based on a global bestseller, follows native New Yorker Rachel Chu to Singapore to meet her boyfriend's family.",
					"PG-13", "https://www.youtube.com/watch?v=ZQ-YX-5bAs0",false,whereToWatch));

			movieRepo.save(new Movie("Spider-Man: Into the Spider-Verse", "Animation, Action, Adventure",
					dateFormat.parse("2018-12-14"), 117,
					"Teen Miles Morales becomes Spider-Man of his reality, crossing his path with five counterparts from other dimensions to stop a threat for all realities.",
					"PG", "https://www.youtube.com/watch?v=tg52up16eq0",false,whereToWatch));
			movieRepo.save(new Movie("The Grand Budapest Hotel", "Comedy, Drama", dateFormat.parse("2014-03-28"), 100,
					"The adventures of Gustave H, a legendary concierge at a famous hotel from the fictional Republic of Zubrowka between the first and second World Wars, and Zero Moustafa, the lobby boy who becomes his most trusted friend.",
					"R", "https://www.youtube.com/watch?v=1Fg5iWmQjwk",false,whereToWatch));
			movieRepo.save(new Movie("Titanic", "Drama", dateFormat.parse("1997-12-19"), 180, "Synopsys here..", "R",
					"https://www.youtube.com/watch?v=I7c1etV7D7g",false,whereToWatch));
			
			*/
			roleRepo.save(new Role(ERole.ROLE_ADMIN));
			roleRepo.save(new Role(ERole.ROLE_USER));

			
			//USER'S SAMPLES
			User user1= new User("aalwen0","aalwen0@twitpic.com","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu","Avrit","Alwen",dateFormat.parse("1988-12-19"),"564-669-3183","23 South Road","Vancouver","BC","Canada");
			User user2= new User("jkeppin1","jkeppin1@forbes.com","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu","Jaclyn","Keppin",dateFormat.parse("1994-09-28"),"428-461-2797","63 Bashford Avenue","Vancouver","BC","Canada");
			User user3= new User("sshaxby2","sshaxby2@taobao.com","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu","Sharleen","Shaxby",dateFormat.parse("1987-08-07"),"843-912-8340","62111 Graceland Point","Vancouver","BC","Canada");
			User user4= new User("bblagburn3","bblagburn3@forbes.com","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu","Brandise","Blagburn",dateFormat.parse("1981-12-03"),"112-574-5074","35866 Truax Plaza","Vancouver","BC","Canada");
			User user5= new User("mdumphries4","mdumphries4@utexas.edu","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu","Monty","Dumphries",dateFormat.parse("1966-07-09"),"381-596-7879","81 Nancy Place","Vancouver","BC","Canada");
			User user6= new User("akitcher5","akitcher5@liveinternet.ru","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu","Alica","Kitcher",dateFormat.parse("1978-01-02"),"747-924-7579","7126 Eliot Plaza","Vancouver","BC","Canada");
			User user7= new User("ujoret6","ujoret6@mashable.com","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu","Ulrick","Joret",dateFormat.parse("1990-01-03"),"292-367-7000","07 Holmberg Junction","Vancouver","BC","Canada");
			User user8= new User("admin123","admin@screenseekers.com","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu","Admin","Admin",null,null,null,null,null,null);

			Set<Role> roles = new HashSet<>();
			Role userRole = roleRepo.findByName(ERole.ROLE_USER)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(userRole);
		          
		    Set<Role> rolesAdmin = new HashSet<>();
			Role adminRole = roleRepo.findByName(ERole.ROLE_ADMIN)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			rolesAdmin.add(adminRole);
			
			Set<Role> bothRoles = new HashSet<>();
			bothRoles.add(userRole);
			bothRoles.add(adminRole);
			
			user1.setRoles(bothRoles);
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
			
			System.out.println("Initial dataset loaded into H2 Database\n");
			
			System.out.println("ScreenSeekers API Server running...\n");
			System.out.println("Important Notes:");
			System.out.println("---------------:\n");
			
			System.out.println("Accessing the DB:");
			System.out.println("To check the H2 Database, please go to the following link: http://localhost:8080/h2-ui");
			System.out.println("JDBC URL: jdbc:h2:mem:screenseekersdb");
			System.out.println("User Name: sa");
			System.out.println("No password required! :) \n");
			
			System.out.println("Testing API's:");
			System.out.println("On the Project Folder, go to Test_API_Resources and import the json file into PostMan (ScreenSeekers-Backend.postman_collection)\n");
			
			System.out.println("Simulation of the Movie Recommendation:");
			System.out.println("1. Access the H2 Database UI");
			System.out.println("2. Copy & Paste the INSERT commands from the file DML_RATINGS_RECOMMENDATIONS_TEST.sql");
			System.out.println("3. Run the API request related to this feature");
		};

	}
	
	

}