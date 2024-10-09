package com.platform.ticket.spring.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.platform.ticket.spring.model.Ticket;
import com.platform.ticket.spring.service.TicketService;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketRestApiController {
	
	@Autowired
	private TicketService service;
	
	@GetMapping
	public ResponseEntity<List<Ticket>> index(@RequestParam(name = "title", required = false) String title,
											  @RequestParam(name = "category", required = false) String category){
		
		List<Ticket> tickets;
		
		if ( ( title != null && !title.isBlank() ) &&  ( category != null && !category.isBlank() )) {
			tickets = service.findListByTitleAndCategoryName(title, category);
		
		} else if ( title != null && !title.isBlank() ){
			tickets = service.findListByTitle(title);
			
		} else if ( category != null && !category.isBlank() ) {
			tickets = service.findListByCategoryName(category);
			
		} else {
			tickets = service.findAll();
		}
		
		if ( tickets.size() > 0 ) {
			return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
