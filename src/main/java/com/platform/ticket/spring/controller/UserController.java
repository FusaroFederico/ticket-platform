package com.platform.ticket.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.platform.ticket.spring.model.Ticket;
import com.platform.ticket.spring.model.User;
import com.platform.ticket.spring.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	// INDEX
	@GetMapping
	public String index(Model model) {
		
		model.addAttribute("operators", userService.getAllOperators());
		return "/users/index";
	}
	
	// SHOW
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer id, Model model) {
		
		
		model.addAttribute("operator", userService.getById(id));
		return "/users/show";
	}
	
	// Update isActive
	@PostMapping("/updateIsActive/{id}")
	public String updateIsActive(@PathVariable("id") Integer id,
								 @RequestParam(name = "isActive", required = true) Boolean isActive,
								 RedirectAttributes redirectAttributes) {
		
		User user = userService.getById(id);
		
		if (user.getTickets().size() > 0) {
			for( Ticket ticket : user.getTickets()) {
				if ( !ticket.getStatus().equals("completato") ) {
					redirectAttributes.addFlashAttribute("non puoi aggiornare il tuo stato");
					return "redirect:/users/" + user.getId();
				}
			}
		}
		
		userService.updateIsActive(user, isActive);
		redirectAttributes.addFlashAttribute("Stato aggiornato con successo.");
		return "redirect:/users/" + user.getId();
	}
}
