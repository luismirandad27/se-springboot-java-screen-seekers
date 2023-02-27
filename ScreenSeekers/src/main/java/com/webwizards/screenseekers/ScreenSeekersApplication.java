/*
 * Class File: ScreenSeekersApplication.java
 * 
 * ------------
 * Description:
 * ------------
 * v1.01: added 20 movies, Rating Repo
 * 
 * @author Victor Chawsukho
 * @version 1.01
 * 
 */

package com.webwizards.screenseekers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.webwizards.screenseekers.model.ERole;
import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.model.Rating;
import com.webwizards.screenseekers.model.RatingSerializer;
import com.webwizards.screenseekers.model.Role;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.RatingRepository;
import com.webwizards.screenseekers.repository.RoleRepository;

@SpringBootApplication
public class ScreenSeekersApplication {
	// this is for parsing input date from ApplicationRunner
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) {
		SpringApplication.run(ScreenSeekersApplication.class, args);
	}

	@Bean
	ApplicationRunner init(RoleRepository roleRepo, MovieRepository movieRepo, RatingRepository ratingRepo) {

		return args -> {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			movieRepo.save(new Movie("The Dark Knight", "Action, Crime, Drama", dateFormat.parse("2008-07-18"), 152,
					"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
					"PG-13", "https://www.youtube.com/watch?v=EXeTwQWrcwY", dateFormat.parse("2021-05-01"), null,
					null));
			movieRepo.save(new Movie("Inception", "Action, Adventure, Sci-Fi", dateFormat.parse("2010-07-16"), 148,
					"A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
					"PG-13", "https://www.youtube.com/watch?v=YoHD9XEInc0", dateFormat.parse("2021-01-21"), null,
					null));
			movieRepo.save(new Movie("The Lord of the Rings: The Return of the King", "Adventure, Drama, Fantasy",
					dateFormat.parse("2003-12-17"), 201,
					"Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.",
					"PG-13", "https://www.youtube.com/watch?v=r5X-hFf6Bwo", dateFormat.parse("2021-06-15"), null,
					null));
			movieRepo.save(new Movie("The Matrix", "Action, Sci-Fi", dateFormat.parse("1999-03-31"), 136,
					"A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
					"R", "https://www.youtube.com/watch?v=m8e-FF8MsqU", dateFormat.parse("2021-08-08"),
					dateFormat.parse("2021-10-12"), null));
			movieRepo.save(new Movie("Interstellar", "Adventure, Drama, Sci-Fi", dateFormat.parse("2014-11-07"), 169,
					"A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
					"PG-13", "https://www.youtube.com/watch?v=zSWdZVtXT7E", dateFormat.parse("2021-03-19"),
					dateFormat.parse("2021-11-22"), null));
			movieRepo.save(new Movie("The Silence of the Lambs", "Crime, Drama, Thriller",
					dateFormat.parse("1991-02-14"), 118,
					"A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.",
					"R", "https://www.youtube.com/watch?v=RuX2MQeb8UM", dateFormat.parse("2021-09-05"), null, null));
			movieRepo.save(new Movie("Avengers: Endgame", "Action, Adventure, Drama", dateFormat.parse("2019-04-26"),
					181,
					"After the devastating events of Avengers: Infinity War (2018), the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to undo Thanos' actions and restore order to the universe.",
					"PG-13", "https://www.youtube.com/watch?v=TcMBFSGVi1c", dateFormat.parse("2021-02-03"), null,
					null));
			movieRepo.save(new Movie("The Shawshank Redemption", "Drama", dateFormat.parse("1994-10-14"), 142,
					"Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
					"R", "https://www.youtube.com/watch?v=6hB3S9bIaco", dateFormat.parse("2021-09-15"),
					dateFormat.parse("2021-09-22"), null));

			movieRepo.save(new Movie("Pulp Fiction", "Crime, Drama", dateFormat.parse("1994-10-14"), 154,
					"The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
					"R", "https://www.youtube.com/watch?v=s7EdQ4FqbhY", dateFormat.parse("2021-03-11"), null, null));
			movieRepo.save(new Movie("La La Land", "Comedy, Drama, Music", dateFormat.parse("2016-12-09"), 128,
					"While navigating their careers in Los Angeles, a pianist and an actress fall in love while attempting to reconcile their aspirations for the future.",
					"PG-13", "https://www.youtube.com/watch?v=0pdqf4P9MB8", dateFormat.parse("2021-08-05"), null,
					null));

			movieRepo.save(new Movie("Inside Out", "Animation, Adventure, Comedy", dateFormat.parse("2015-06-19"), 95,
					"After young Riley is uprooted from her Midwest life and moved to San Francisco, her emotions - Joy, Fear, Anger, Disgust and Sadness - conflict on how best to navigate a new city, house, and school.",
					"PG", "https://www.youtube.com/watch?v=yRUAzGQ3nSY", dateFormat.parse("2021-11-17"),
					dateFormat.parse("2022-01-08"), null));

			movieRepo.save(new Movie("The Hunger Games", "Action, Adventure, Sci-Fi", dateFormat.parse("2012-03-23"),
					142,
					"Katniss Everdeen voluntarily takes her younger sister's place in the Hunger Games: a televised competition in which two teenagers from each of the twelve Districts of Panem are chosen at random to fight to the death.",
					"PG-13", "https://www.youtube.com/watch?v=mfmrPu43DF8", dateFormat.parse("2021-06-25"),
					dateFormat.parse("2021-07-02"), null));

			movieRepo.save(new Movie("Toy Story 3", "Animation, Adventure, Comedy", dateFormat.parse("2010-06-18"), 103,
					"The toys are mistakenly delivered to a day-care center instead of the attic right before Andy leaves for college, and it's up to Woody to convince the other toys that they weren't abandoned and to return home.",
					"G", "https://www.youtube.com/watch?v=JcpWXaA2qeg", dateFormat.parse("2021-04-12"),
					dateFormat.parse("2021-06-01"), null));

			movieRepo.save(new Movie("Black Panther", "Action, Adventure, Sci-Fi", dateFormat.parse("2018-02-16"), 134,
					"T'Challa, heir to the hidden but advanced kingdom of Wakanda, must step forward to lead his people into a new future and confront a challenger from his country's past.",
					"PG-13", "https://www.youtube.com/watch?v=xjDjIWPwcPU", dateFormat.parse("2021-03-18"), null,
					null));

			movieRepo.save(new Movie("Coco", "Animation, Adventure, Family", dateFormat.parse("2017-11-22"), 105,
					"Aspiring musician Miguel, confronted with his family's ancestral ban on music, enters the Land of the Dead to find his great-great-grandfather, a legendary singer.",
					"PG", "https://www.youtube.com/watch?v=xlnPHQ3TLX8", dateFormat.parse("2021-02-20"),
					dateFormat.parse("2021-04-01"), null));
			movieRepo.save(new Movie("The Imitation Game", "Biography, Drama, Thriller", dateFormat.parse("2014-11-28"),
					114,
					"During World War II, the English mathematical genius Alan Turing tries to crack the German Enigma code with help from fellow mathematicians.",
					"PG-13", "https://www.youtube.com/watch?v=S5CjKEFb-sM", dateFormat.parse("2021-09-02"),
					dateFormat.parse("2021-12-01"), null));

			movieRepo.save(new Movie("Mad Max: Fury Road", "Action, Adventure, Sci-Fi", dateFormat.parse("2015-05-15"),
					120,
					"In a post-apocalyptic wasteland, a woman rebels against a tyrannical ruler in search for her homeland with the aid of a group of female prisoners, a psychotic worshiper, and a drifter named Max.",
					"R", "https://www.youtube.com/watch?v=hEJnMQG9ev8", dateFormat.parse("2021-05-10"), null, null));
			movieRepo.save(new Movie("Crazy Rich Asians", "Comedy, Drama, Romance", dateFormat.parse("2018-08-15"), 120,
					"This contemporary romantic comedy, based on a global bestseller, follows native New Yorker Rachel Chu to Singapore to meet her boyfriend's family.",
					"PG-13", "https://www.youtube.com/watch?v=ZQ-YX-5bAs0", dateFormat.parse("2021-11-22"),
					dateFormat.parse("2022-01-05"), null));

			movieRepo.save(new Movie("Spider-Man: Into the Spider-Verse", "Animation, Action, Adventure",
					dateFormat.parse("2018-12-14"), 117,
					"Teen Miles Morales becomes Spider-Man of his reality, crossing his path with five counterparts from other dimensions to stop a threat for all realities.",
					"PG", "https://www.youtube.com/watch?v=tg52up16eq0", dateFormat.parse("2021-03-25"), null, null));
			movieRepo.save(new Movie("The Grand Budapest Hotel", "Comedy, Drama", dateFormat.parse("2014-03-28"), 100,
					"The adventures of Gustave H, a legendary concierge at a famous hotel from the fictional Republic of Zubrowka between the first and second World Wars, and Zero Moustafa, the lobby boy who becomes his most trusted friend.",
					"R", "https://www.youtube.com/watch?v=1Fg5iWmQjwk", dateFormat.parse("2021-09-15"), null, null));
			movieRepo.save(new Movie("Titanic", "Drama", dateFormat.parse("1997-12-19"), 180, "Synopsys here..", "R",
					"https://www.youtube.com/watch?v=I7c1etV7D7g", dateFormat.parse("2023-02-06"),
					dateFormat.parse("2023-02-06"), dateFormat.parse("2023-02-06")));
      
			roleRepo.save(new Role(ERole.ROLE_ADMIN));
			roleRepo.save(new Role(ERole.ROLE_USER));

		};

	}

}
