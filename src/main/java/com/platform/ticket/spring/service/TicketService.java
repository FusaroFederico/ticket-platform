package com.platform.ticket.spring.service;

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

	public List<Ticket> findAll(){
		return repository.findAll();
	}
	
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
		ticket.setStatus("da fare");
		return repository.save(ticket);
	}
	
	public Ticket update(Ticket ticket) {
		return repository.save(ticket);
	}
	
	public Ticket updateStatus(Ticket ticket, String status) {
		ticket.setStatus(status);
		return repository.save(ticket);
	}
	
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
}
