package com.platform.ticket.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import com.platform.ticket.spring.security.DatabaseUserDetails;
import com.platform.ticket.spring.service.CategoryService;
import com.platform.ticket.spring.service.TicketService;
import com.platform.ticket.spring.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	
	// INDEX
	@GetMapping
	public String index(@AuthenticationPrincipal DatabaseUserDetails currentUser, 
						 Model model, 
						 @RequestParam(name = "title", required = false) String title) {
		
		if(currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
			if (title != null && !title.isBlank() ) {
				model.addAttribute("tickets", ticketService.findListByTitle(title));
			} else {
				model.addAttribute("tickets", ticketService.findAllSortedByCreationDate());
			}
		} else {
			model.addAttribute("tickets", userService.getById(currentUser.getId()).getTickets());
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
		
		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("operatorsList", userService.getAllOperators());
		model.addAttribute("ticket", new Ticket());
		return "/tickets/create";
	}
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Ticket ticket,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes,
						Model model) {
		
		if (bindingResult.hasFieldErrors("title") || bindingResult.hasFieldErrors("description") || bindingResult.hasFieldErrors("user") || bindingResult.hasFieldErrors("categories")) {
			model.addAttribute("categories", categoryService.findAll());
			model.addAttribute("operatorsList", userService.getAllOperators());
			return "/tickets/create";
		}
		
		ticketService.create(ticket);
		return "redirect:/tickets";
	}
	
	// EDIT
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		
		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("ticket", ticketService.getById(id));
		model.addAttribute("operatorsList", userService.getAllOperators());
		return "/tickets/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ticket") Ticket updateTicket,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes,
						Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryService.findAll());
			model.addAttribute("operatorsList", userService.getAllOperators());
			return "/tickets/edit";
		}
		
		ticketService.update(updateTicket);
		
		return "redirect:/tickets/" + updateTicket.getId();
	}
	
	// Update Status
	@PostMapping("/updateStatus/{id}")
	public String updateStatus(@PathVariable("id") Integer id,
							   @RequestParam(name = "status", required = true) String status,
							   RedirectAttributes redirectAttributes) {
		
		ticketService.updateStatus(ticketService.getById(id), status);
		
		return "redirect:/tickets/" + id;
	}
}
