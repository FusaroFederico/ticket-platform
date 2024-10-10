package com.platform.ticket.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.platform.ticket.spring.model.Role;

public interface RoleRepository extends JpaRepository<Role , Integer> {
	
	public Optional<Role> findByName(String roleName);

}
