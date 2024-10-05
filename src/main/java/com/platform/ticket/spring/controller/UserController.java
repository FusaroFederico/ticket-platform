package com.platform.ticket.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.platform.ticket.spring.model.Ticket;
import com.platform.ticket.spring.model.User;
import com.platform.ticket.spring.security.DatabaseUserDetails;
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
		
		model.addAttribute("title", "Dettagli Operatore");
		model.addAttribute("operator", userService.getById(id));
		
		return "/users/show";
	}
	
	// Update isActive
	@PostMapping("/updateIsActive")
	public String updateIsActive(@AuthenticationPrincipal DatabaseUserDetails currentUser,
								 @RequestParam(name = "isActive", required = true) Boolean isActive,
								 RedirectAttributes redirectAttributes) {
		
		User user = userService.getById(currentUser.getId());
		
		if (user.getTickets().size() > 0 && !isActive) {
			for( Ticket ticket : user.getTickets()) {
				if ( !ticket.getStatus().equals("completato") ) {
					redirectAttributes.addFlashAttribute("alertMessage", "Impossibile impostare lo stato su 'non attivo' se hai almeno un ticket con stato 'da fare' o 'in corso'!");
					redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
					return "redirect:/users/profile";
				}
			}
		}
		
		userService.updateIsActive(user, isActive);
		redirectAttributes.addFlashAttribute("alertMessage", "Stato aggiornato con successo.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/users/profile";
	}
	
	// Show User Profile
	@GetMapping("/profile")
	public String showUserProfile(@AuthenticationPrincipal DatabaseUserDetails currentUser, Model model) {
		
		model.addAttribute("title", "Profilo Utente");
		model.addAttribute("operator", userService.getById(currentUser.getId()));
		
		return "/users/show";
	}
	
	}
}
