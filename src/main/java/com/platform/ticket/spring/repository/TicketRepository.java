package com.platform.ticket.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.platform.ticket.spring.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>{

	// custom queries
	public List<Ticket> findByTitleContainingIgnoreCaseOrderByCreatedAtAsc(String name);
}
