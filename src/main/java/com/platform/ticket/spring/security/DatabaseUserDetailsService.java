package com.platform.ticket.spring.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.platform.ticket.spring.model.User;
import com.platform.ticket.spring.repository.UserRepository;


@Service
public class DatabaseUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
	Optional<User> user = repository.findByEmail(email);
	
	if( user.isPresent() ) {
		return new DatabaseUserDetails(user.get());
	} else {
		throw new UsernameNotFoundException("Username not found");
	}
	
	}
}
