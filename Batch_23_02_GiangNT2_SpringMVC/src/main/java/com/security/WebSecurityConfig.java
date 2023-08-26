package com.security;

/** 
 * WebSecurityConfig
 * 
 * Version 0.0.1-SNAPSHOT
 * 
 * Date: 28-4-2023
 * 
 * Copyright 
 * 
 * Modification Logs:
 * 
 * DATE                 AUTHOR          DESCRIPTION
 * -----------------------------------------------------------------------
 * 09-06-2023              GiangNT2            Create
 *  
 * */
import org.springframework.beans.factory.annotation.Autowired;
/** 
 * Login
 * 
 * Version 0.0.1-SNAPSHOT
 * 
 * Date: 28-5-2023
 * 
 * Copyright 
 * 
 * Modification Logs:
 * 
 * DATE                 AUTHOR          DESCRIPTION
 * -----------------------------------------------------------------------
 * 06-05-2023              GiangNT2            Create
 *  
 * */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	/**
	 * 
	 * The UserDetailsService implementation used for retrieving user details. It is
	 * autowired by the application context.
	 */
	@Autowired
	UserDetailsService detailsService;

	/**
	 * 
	 * Configures the security filter chain for the application.
	 * 
	 * @param http The HttpSecurity object used for configuring security settings.
	 * 
	 * @return The SecurityFilterChain object representing the configured security
	 *         filter chain.
	 * 
	 * @throws Exception if an error occurs during the configuration.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		RequestMatcher adminUrls = new AntPathRequestMatcher("/admin");
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/assets/**", "/imgs/**", "/registerLogin", "/authenticationemail").permitAll()
				.requestMatchers(adminUrls).hasRole("ADMIN").anyRequest().authenticated())
				.formLogin((form) -> form.loginPage("/login").permitAll().defaultSuccessUrl("/home"))
				.logout((logout) -> logout.invalidateHttpSession(true).clearAuthentication(true).permitAll()
						.permitAll())
				.rememberMe((rememberMe) -> rememberMe.userDetailsService(detailsService).key("myRememberMeKey"));

		return http.build();
	}

	/**
	 * 
	 * Creates a BCryptPasswordEncoder instance for password encoding.
	 * 
	 * @return The PasswordEncoder implementation for BCrypt hashing.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 
	 * Configures the global authentication manager builder with a custom
	 * UserDetailsService.
	 * 
	 * @param auth               The AuthenticationManagerBuilder object to
	 *                           configure.
	 * @param userDetailsService The UserDetailsService implementation for
	 *                           retrieving user details.
	 * @throws Exception if an error occurs during the configuration.
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService)
			throws Exception {
		auth.userDetailsService(detailsService);
	}
}
