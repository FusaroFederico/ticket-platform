package com.platform.ticket.spring.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.platform.ticket.spring.model.Ticket;
import com.platform.ticket.spring.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository repository;

	public List<Ticket> findAllSortedByCreationDate(){
		return repository.findAll(Sort.by("createdAt"));
	}
	
	public List<Ticket> findListByTitle(String title){
		return repository.findByTitleContainingIgnoreCaseOrderByCreatedAtAsc(title);
	}
	
	public Ticket getById(Integer id) {
		return repository.findById(id).get();
	}
	
	public Ticket create(Ticket ticket) {
		return repository.save(ticket);
	}
	
	public Ticket update(Ticket ticket) {
		ticket.setUpdatedAt(LocalDateTime.now());
		return repository.save(ticket);
	}
	
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
}
