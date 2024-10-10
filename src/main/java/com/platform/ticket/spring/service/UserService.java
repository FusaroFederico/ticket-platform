package com.platform.ticket.spring.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.ticket.spring.model.Role;
import com.platform.ticket.spring.model.User;
import com.platform.ticket.spring.repository.RoleRepository;
import com.platform.ticket.spring.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	@Autowired
	private RoleRepository roleRepository;

	public List<User> findAll(){
		return repository.findAll();
	}
	
	public List<User> getAllOperators(){
		return repository.findByRoles_Name("OPERATOR");
	}
	
	public User getById(Integer id) {
		return repository.findById(id).get();
	}
	
	public User createNewOperator(User newOperator) {
		
		newOperator.setIsActive(false);
		newOperator.setProfilePicUrl("https://picsum.photos/id/0/200/300");
		Set<Role> roles = new HashSet<Role>();
		roles.add( roleRepository.findByName("OPERATOR").get() );
		newOperator.setRoles(roles);
		
		return repository.save(newOperator);
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
	
	public User updatePassword (User user, String newPassword) {
		user.setPassword(newPassword);
		return repository.save(user);
	}
	
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
	
}
