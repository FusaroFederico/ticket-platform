package com.platform.ticket.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.platform.ticket.spring.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
