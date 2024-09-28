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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.platform.ticket.spring.model.Ticket;
import com.platform.ticket.spring.service.TicketService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	// INDEX
	@GetMapping
	public String index(Model model, @RequestParam(name = "title", required = false) String title) {
		
		if (title != null && !title.isBlank() ) {
			model.addAttribute("tickets", ticketService.findListByTitle(title));
		} else {
			model.addAttribute("tickets", ticketService.findAllSortedByCreationDate());
		}
		
		return "/tickets/index";
	}
	
	// SHOW
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		
		model.addAttribute("ticket", ticketService.getById(id));
		return "/tickets/show";
	}
	
	// CREATE
	@GetMapping("/create")
	public String create(Model model) {
		
		model.addAttribute("ticket", new Ticket());
		return "/tickets/create";
	}
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Ticket newTicket,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return "/tickets/create";
		}
		
		ticketService.create(newTicket);
		return "redirect:/tickets";
	}
	
	// EDIT
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		
		model.addAttribute("ticket", ticketService.getById(id));
		return "/tickets/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ticket") Ticket updateTicket,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return "/tickets/edit";
		}
		
		ticketService.update(updateTicket);
		
		return "redirect:/tickets/" + updateTicket.getId();
	}
	
}
