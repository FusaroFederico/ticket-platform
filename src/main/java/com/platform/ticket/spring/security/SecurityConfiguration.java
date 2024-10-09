package com.platform.ticket.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
        	.authorizeHttpRequests((requests) -> requests
        		.requestMatchers("/tickets/create", "/tickets/edit/**", "/users").hasAuthority("ADMIN")
        		.requestMatchers("/", "/tickets", "/tickets/{id}", "/tickets/updateStatus/{id}", "/users/updateIsActive", "/users/profile", "/users/updatePassword").hasAnyAuthority("OPERATOR", "ADMIN")
        		.requestMatchers("/users/{id}").hasAuthority("ADMIN")
        		.requestMatchers("/**").permitAll()
				.anyRequest().authenticated()
        		)
			.formLogin((form) -> form
					.loginPage("/login")
					.permitAll()
					)
			.logout((logout) -> logout
					.logoutUrl("/logout")                                            
		            .logoutSuccessUrl("/login?logout")
					.permitAll());

		return http.build();
	}
	
	@Bean
	DatabaseUserDetailsService userDetailsService() {
		return new DatabaseUserDetailsService();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
}
