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
					"PG-13", "https://www.youtube.com/embed/EXeTwQWrcwY",false,false,false,whereToWatch, null, null);
			
			Movie movie2 = new Movie("Inception", "Action, Adventure, Sci-Fi", dateFormat.parse("2010-07-16"), 148,
					"A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
					"PG-13", "https://www.youtube.com/embed/YoHD9XEInc0",false,false,false,whereToWatch, null, null);
			
			Movie movie3 = new Movie("The Lord of the Rings: The Return of the King", "Adventure, Drama, Fantasy",
					dateFormat.parse("2003-12-17"), 201,
					"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.",
					"PG-13", "https://www.youtube.com/embed/r5X-hFf6Bwo",false,false,false,whereToWatch, null, null);
			
			Movie movie4 = new Movie("The Matrix", "Action, Sci-Fi", dateFormat.parse("1999-03-31"), 136,
					"A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
					"R", "https://www.youtube.com/embed/m8e-FF8MsqU",false,false,false,whereToWatch, null, null);
			
			Movie movie5 = new Movie("Interstellar", "Adventure, Drama, Sci-Fi", dateFormat.parse("2014-11-07"), 169,
					"A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
					"PG-13", "https://www.youtube.com/embed/zSWdZVtXT7E",false,false,false,whereToWatch, null, null);
			
			Movie movie6 = new Movie("The Silence of the Lambs", "Crime, Drama, Thriller",
					dateFormat.parse("1991-02-14"), 118,
					"A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.",
					"R", "https://www.youtube.com/embed/RuX2MQeb8UM",false,false,false,whereToWatch, null, null);
			
			Movie movie7 = new Movie("Forrest Gump", "Drama, Romance", dateFormat.parse("1994-07-06"), 142,
					"The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate and other historical events unfold through the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.",
					"PG-13", "https://www.youtube.com/embed/bLvqoHBptjg",false,false,false,whereToWatch, null, null);

			Movie movie8 = new Movie("The Shawshank Redemption", "Drama", dateFormat.parse("1994-10-14"), 142,
			"Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
			"R", "https://www.youtube.com/embed/6hB3S9bIaco",false,false,false,whereToWatch, null, null);

			Movie movie9 = new Movie("Pulp Fiction", "Crime, Drama", dateFormat.parse("1994-10-14"), 154,
			"The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
			"R", "https://www.youtube.com/embed/s7EdQ4FqbhY",false,false,false,whereToWatch, null, null);

			Movie movie10 = new Movie("The Godfather", "Crime, Drama", dateFormat.parse("1972-03-24"), 175,
			"The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
			"R", "https://www.youtube.com/embed/sY1S34973zA",false,false,false,whereToWatch, null, null);

			Movie movie11 = new Movie("The Godfather: Part II", "Crime, Drama", dateFormat.parse("1974-12-20"), 202,
			"The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.",
			"R", "https://www.youtube.com/embed/qJr92K_hKl0",false,false,false,whereToWatch, null, null);

			Movie movie12 = new Movie("The Prestige", "Drama, Mystery, Sci-Fi", dateFormat.parse("2006-10-20"), 130,
			"Two stage magicians engage in competitive one-upmanship in an attempt to create the ultimate stage illusion.",
			"PG-13", "https://www.youtube.com/embed/o4gHCmTQDVI",false,false,false,whereToWatch, null, null);

			Movie movie13 = new Movie("Fight Club", "Drama", dateFormat.parse("1999-10-15"), 139,
			"An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more.",
			"R", "https://www.youtube.com/embed/SUXWAEX2jlg",false,false,false,whereToWatch, null, null);
			
			
			//Simulation (on theathers)
			Movie movie14 = new Movie("Doctor Strange in the Multiverse of Madness", "Action, Adventure, Fantasy",
	                  dateFormat.parse("2022-03-25"), 152,
	                  "After the events of Spider-Man: No Way Home, Dr. Strange continues his research on the Time Stone. But an old friend turned enemy seeks to destroy every sorcerer on Earth, messing with Strange's plan.",
	                  "PG-13", "https://www.youtube.com/embed/aWzlQ2N6qqg", true, false, false, whereToWatch, null, null);
			
			Movie movie15 = new Movie("Avatar: The Way of Water", "Action, Adventure, Fantasy",
	                  dateFormat.parse("2022-12-16"), 160,
	                  "Taking place a few years after the events of the original Avatar movie, this film follows Jake Sully and Neytiri as they explore more of the otherworldly planet of Pandora and encounter new threats and allies.",
	                  "PG-13", "https://www.youtube.com/embed/a8Gx8wiNbs8", true, false, false, whereToWatch, null, null);
			
			Movie movie16 = new Movie("Jurassic World: Dominion", "Action, Adventure, Sci-Fi",
	                  dateFormat.parse("2022-06-10"), 151,
	                  "The dinosaurs have escaped from Isla Nublar and now roam free in the world. As chaos ensues, the original Jurassic Park characters come together to stop the impending disaster.",
	                  "PG-13", "https://www.youtube.com/embed/fb5ELWi-ekk", true, false, false, whereToWatch, null, null);
			
			Movie movie17 = new Movie("The Batman", "Action, Crime, Drama",
	                  dateFormat.parse("2022-03-04"), 164,
	                  "A new take on the Batman story, this film follows a young Bruce Wayne as he navigates his way through Gotham's corrupt underbelly and tries to stop a deadly villain known as The Riddler.",
	                  "R", "https://www.youtube.com/embed/mqqft2x_Aa4", true, false, false, whereToWatch, null, null);

			Movie movie18 = new Movie(
				    "The Queen's Gambit",
				    "Drama",
				    dateFormat.parse("2020-10-23"),
				    394,
				    "In the 1950s, orphaned chess prodigy Beth Harmon struggles with addiction in a quest to become the greatest chess player in the world.",
				    "TV-MA",
				    "https://www.youtube.com/embed/oZn3qSgmLqI",
				    false,
				    true,
				    false,
				    new String[] {"Netflix"},null, null
				);

			
			Movie movie19 = new Movie(
				    "Dune",
				    "Science Fiction, Adventure",
				    dateFormat.parse("2021-10-22"),
				    155,
				    "Feature adaptation of Frank Herbert's science fiction novel, about the son of a noble family entrusted with the protection of the most valuable asset and most vital element in the galaxy.",
				    "PG-13",
				    "https://www.youtube.com/embed/8g18jFHCLXk",
				    false,
				    true,
				    false,
				    new String[] {"HBO Max"},null, null
				);

			Movie movie20 = new Movie(
				    "Red Notice",
				    "Action, Adventure, Comedy",
				    dateFormat.parse("2021-11-12"),
				    118,
				    "An Interpol agent tracks the world's most wanted art thief.",
				    "PG-13",
				    "https://www.youtube.com/embed/Pj0wz7zu3Ms",
				    false,
				    true,
				    false,
				    new String[] {"Netflix"},null, null
				);
			
			Movie movie21 = new Movie(
				    "The Power of the Dog",
				    "Drama, Western",
				    dateFormat.parse("2021-11-17"),
				    126,
				    "Charismatic rancher Phil Burbank inspires fear and awe in those around him. When his brother brings home a new wife and her son, Phil torments them until he finds himself exposed to the possibility of love.",
				    "R",
				    "https://www.youtube.com/embed/LRDPo0CHrko",
				    false,
				    true,
				    false,
				    new String[] {"Netflix"},null, null
				);
			
			Movie movie22 = new Movie("Black Panther: Wakanda Forever", "Action, Adventure, Drama",
					dateFormat.parse("2022-11-10"), 0,
					"The sequel to the 2018 superhero hit Black Panther, which will explore the world of Wakanda after the death of King T'Challa.",
					"PG-13", "https://www.youtube.com/embed/_Z3QKkl1WyM",false,false,true, whereToWatch, null, null);
			
			Movie movie23 = new Movie("The Flash", "Action, Adventure, Fantasy",
					dateFormat.parse("2022-11-03"), 0,
					"Ezra Miller stars as the DC superhero The Flash, who gains superhuman speed after being struck by lightning and uses his abilities to fight crime and save the world.",
					"PG-13", "https://www.youtube.com/embed/hebWYacbdvc",false,false,true, whereToWatch, null, null);

			
			Movie movie24 = new Movie("Fantastic Beasts: The Secrets of Dumbledore", "Adventure, Family, Fantasy",
					dateFormat.parse("2022-04-14"), 0,
					"The third installment of the Fantastic Beasts series, in which Newt Scamander teams up with a young Albus Dumbledore to battle the dark wizard Gellert Grindelwald.",
					"PG-13", "https://www.youtube.com/embed/Y9dr2zw-TXQ",false,false,true, whereToWatch, null, null);
			
			movieRepo.save(movie1);
			movieRepo.save(movie2);
			movieRepo.save(movie3);
			movieRepo.save(movie4);
			movieRepo.save(movie5);
			movieRepo.save(movie6);
			movieRepo.save(movie7);
			movieRepo.save(movie8);
			movieRepo.save(movie9);
			movieRepo.save(movie10);
			movieRepo.save(movie11);
			movieRepo.save(movie12);
			movieRepo.save(movie13);
			movieRepo.save(movie14);
			movieRepo.save(movie15);
			movieRepo.save(movie16);
			movieRepo.save(movie17);
			movieRepo.save(movie18);
			movieRepo.save(movie19);
			movieRepo.save(movie20);
			movieRepo.save(movie21);
			movieRepo.save(movie22);
			movieRepo.save(movie23);
			movieRepo.save(movie24);

			
			roleRepo.save(new Role(ERole.ROLE_ADMIN));
			roleRepo.save(new Role(ERole.ROLE_USER));

			
			//USER'S SAMPLES
			User user1= new User("lmirandad","lmirandad27@gmail.com","$2a$10$uZ24huk8z6RtS84muhRADeoMe88ugfM0W13C2L1Olstp4R5hZ.qnu","Luis","Miranda",dateFormat.parse("1993-03-01"),"564-669-3183","23 South Road","Vancouver","BC","Canada");
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