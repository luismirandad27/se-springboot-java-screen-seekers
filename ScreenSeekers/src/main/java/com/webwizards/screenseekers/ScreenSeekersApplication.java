package com.webwizards.screenseekers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.webwizards.screenseekers.model.ERole;
import com.webwizards.screenseekers.model.Movie;
import com.webwizards.screenseekers.model.Role;
import com.webwizards.screenseekers.repository.MovieRepository;
import com.webwizards.screenseekers.repository.RoleRepository;

@SpringBootApplication
public class ScreenSeekersApplication {
	//this is for parsing input date from ApplicationRunner
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) {
		SpringApplication.run(ScreenSeekersApplication.class, args);
	}
	
	@Bean
	ApplicationRunner init(RoleRepository roleRepo, MovieRepository movieRepo) {
		return args -> {
			roleRepo.save(new Role(ERole.ROLE_ADMIN));
			roleRepo.save(new Role(ERole.ROLE_USER));
			

			movieRepo.save(new Movie("Titanic", "Drama", dateFormat.parse("1997-12-19"), 180, "Synopsys here..", "R","https://www.youtube.com/watch?v=I7c1etV7D7g", dateFormat.parse("2023-02-06"), dateFormat.parse("2023-02-06"), dateFormat.parse("2023-02-06") ));

			
		};
				
	}

}
