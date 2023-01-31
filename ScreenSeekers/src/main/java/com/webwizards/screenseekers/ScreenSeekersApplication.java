package com.webwizards.screenseekers;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.webwizards.screenseekers.model.ERole;
import com.webwizards.screenseekers.model.Role;
import com.webwizards.screenseekers.repository.RoleRepository;

@SpringBootApplication
public class ScreenSeekersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScreenSeekersApplication.class, args);
	}
	
	@Bean
	ApplicationRunner init(RoleRepository roleRepo) {
		return args -> {
			roleRepo.save(new Role(ERole.ROLE_ADMIN));
			roleRepo.save(new Role(ERole.ROLE_USER));
		};
				
	}

}
