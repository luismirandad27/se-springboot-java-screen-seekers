package com.webwizards.screenseekers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.webwizards.screenseekers.security.jwt.AuthEntryPointJwt;
import com.webwizards.screenseekers.security.jwt.AuthTokenFilter;
import com.webwizards.screenseekers.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class WebSecurityConfig  {
	 @Autowired
	  UserDetailsServiceImpl userDetailsService;

	  @Autowired
	  private AuthEntryPointJwt unauthorizedHandler;
	  
	  @Value("${spring.h2.console.path}")
	  private String h2ConsolePath;

    @Bean
    AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
      @Bean
      DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
      
      @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
      @Bean
      SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	  
    	  http.cors().and().csrf().disable().headers().frameOptions().disable().and()
          .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
          .authorizeHttpRequests()
          .requestMatchers("/api/auth/**").permitAll()
          .requestMatchers("/api/**").permitAll()
          .requestMatchers("/h2-ui/**").permitAll()
          .requestMatchers("/**").permitAll()
          .anyRequest().authenticated();

      http.authenticationProvider(authenticationProvider());

      http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

      return http.build();
    }
}