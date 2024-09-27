package com.platform.ticket.spring.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull 
	@Email
	@Column(name = "email", nullable = false)
	private String email;
	
	@NotBlank
	@Size(min = 8, max = 30)
	@Column(name = "password", nullable = false)
	private String password;
	
	@NotBlank
	@Size(min = 2, max = 30)
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@NotBlank
	@Size(min = 2, max = 30)
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@NotBlank
	@Column(name = "profile_pic_url", nullable = false)
	private String profilePicUrl;
	
	@NotNull
	@Column(name = "is_available", nullable = false)
	private boolean isAvailable;
	
	@Column(name = "registration_date", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime registrationDate;
	
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE })
	private List<Ticket> tickets;
	
	@OneToMany(mappedBy = "note", cascade = { CascadeType.REMOVE })
	private List<Note> notes;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;
	
	// Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfilePicUrl() {
		return profilePicUrl;
	}

	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
