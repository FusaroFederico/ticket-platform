package com.platform.ticket.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.platform.ticket.spring.model.Note;
import com.platform.ticket.spring.service.NoteService;
import com.platform.ticket.spring.service.TicketService;
import com.platform.ticket.spring.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NoteController {
	
	@Autowired
	private NoteService noteService;
	@Autowired
	private TicketService ticketService;
	@Autowired
	private UserService userService;
	
	// Create note for a ticket with specific id
	@GetMapping("/create/{ticketId}")
	public String createNote(@PathVariable("ticketId") Integer ticketId,
							  Model model) {
		
		model.addAttribute("ticketId", ticketId);
		model.addAttribute("note", new Note());
		
		return "/notes/create";
	}
	
	@PostMapping("/create/{ticketId}")
	public String store(@Valid @ModelAttribute("note") Note noteForm,
							@PathVariable("ticketId") Integer ticketId,
							BindingResult bindingResult,
							RedirectAttributes redirectAttributes,
							Model model) {
		
		
		if (bindingResult.hasFieldErrors("message")) {
		    model.addAttribute("ticketId", ticketId);
		    return "/notes/create";
		}
		
		noteForm.setUser(userService.getById(1));
		noteForm.setTicket(ticketService.getById(ticketId));
		noteService.create(noteForm);
		
		return "redirect:/tickets/" + ticketId ;
	}
}
