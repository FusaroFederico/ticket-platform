package com.platform.ticket.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.platform.ticket.spring.security.DatabaseUserDetails;
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
						BindingResult bindingResult,
						@PathVariable("ticketId") Integer ticketId,
						RedirectAttributes redirectAttributes,
						Model model,
						@AuthenticationPrincipal DatabaseUserDetails userDetails) {
		
		
		if (bindingResult.hasFieldErrors("message")) {
		    model.addAttribute("ticketId", ticketId);
		    return "/notes/create";
		}
		
		noteForm.setUser(userService.getById(userDetails.getId()));
		noteForm.setTicket(ticketService.getById(ticketId));
		noteService.create(noteForm);
		redirectAttributes.addFlashAttribute("alertMessage", "La nota è stata aggiunta con successo.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/tickets/" + ticketId ;
	}
}
