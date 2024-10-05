package com.platform.ticket.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.ticket.spring.model.User;
import com.platform.ticket.spring.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;

	public List<User> findAll(){
		return repository.findAll();
	}
	
	public List<User> getAllOperators(){
		return repository.findByRoles_Name("OPERATOR");
	}
	
	public User getById(Integer id) {
		return repository.findById(id).get();
	}
	
	public User create(User user) {
		return repository.save(user);
	}
	
	public User update(User user) {
		return repository.save(user);
	}
	
	public User updateIsActive(User user, Boolean isActive) {
		user.setIsActive(isActive);
		return repository.save(user);
	}
	
	public User updateUserInfo(User user, String firstName, String lastName, String email, String profilePicUrl) {
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setProfilePicUrl(profilePicUrl);
		return repository.save(user);
	}
	
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
	
}
