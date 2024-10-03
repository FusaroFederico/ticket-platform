package com.platform.ticket.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.platform.ticket.spring.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public Optional<User> findByEmail(String email);
	
	//@Query(SELECT * 
	//       FROM users
	//       JOIN users_roles ON users.id = users_roles.user_id
	//       JOIN role ON users_roles.role_id = role.id
	//       WHERE role.name = "role";)
	public List<User> findByRoles_Name(String role);
}
