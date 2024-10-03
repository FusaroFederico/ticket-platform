package com.platform.ticket.spring.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.platform.ticket.spring.model.Role;
import com.platform.ticket.spring.model.User;


@SuppressWarnings("serial")
public class DatabaseUserDetails implements UserDetails{
	
	private final Integer id;
	private final String email;
	private final String password;
	private final String firstName;
	private final String lastName;
	private final Boolean isActive;
	private final String profilePicUrl;
	private final Set<GrantedAuthority> authorities;
	
	public DatabaseUserDetails(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.isActive = user.getIsActive();
		this.profilePicUrl = user.getProfilePicUrl();
		
		authorities = new HashSet<GrantedAuthority>();
		for(Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}
	
	public String getProfilePicUrl() {
		return this.profilePicUrl;
	}
}
