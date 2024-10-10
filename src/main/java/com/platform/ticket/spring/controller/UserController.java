package com.platform.ticket.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.platform.ticket.spring.model.User;
import com.platform.ticket.spring.security.DatabaseUserDetails;
import com.platform.ticket.spring.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	
	// Edit user profile
	@GetMapping("/edit/profile")
	public String editUserProfile(@AuthenticationPrincipal DatabaseUserDetails currentUser, Model model) {
		
		model.addAttribute("user", userService.getById(currentUser.getId()));
		return "/users/edit";
	}
	
	@PostMapping("/edit/profile")
	public String updateUserProfile(@Valid @ModelAttribute("user") User updateUser,
									BindingResult bindingResult,
									RedirectAttributes redirectAttributes,
									@AuthenticationPrincipal DatabaseUserDetails currentUser) {
		
		if (bindingResult.hasFieldErrors("firstName") || bindingResult.hasFieldErrors("lastName") || bindingResult.hasFieldErrors("email") || bindingResult.hasFieldErrors("profilePicUrl")) {
			return "/users/edit";
		}
		
		try {
			
			userService.updateUserInfo(userService.getById(currentUser.getId()), updateUser.getFirstName(), updateUser.getLastName(), updateUser.getEmail(), updateUser.getProfilePicUrl());
			redirectAttributes.addFlashAttribute("alertMessage", "Informazioni personali aggiornate con successo.");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/users/profile";
			
		} catch (DataIntegrityViolationException ex) {
			
			bindingResult.rejectValue("email" , "error.user", "Email già in uso da un altro account.");
			return "/users/edit";
			
		}
	}
	
	// Update password field
	@GetMapping("/updatePassword")
	public String updatePasswordForm() {
		return "/users/update-password";
	}
	
	@PostMapping("/updatePassword")
	public String storeNewPassword(@AuthenticationPrincipal DatabaseUserDetails currentUser,
			 					   @RequestParam(name = "oldPassword", required = true) String oldPassword,
			 					   @RequestParam(name = "newPassword", required = true) String newPassword,
			 					   RedirectAttributes redirectAttributes,
			 					   Model model) {
		
		if ( !passwordEncoder.matches(oldPassword, currentUser.getPassword() ) ) {
			model.addAttribute("oldPasswordNotMatch", "Password Errata!");
			return "/users/update-password";
		}
		
		// new password validation
		if ( newPassword == null || newPassword.isBlank() ) {
			model.addAttribute("newPasswordError", "Non può essere vuota!");
			return "/users/update-password";
		} else if ( newPassword.length() < 8) {
			model.addAttribute("newPasswordError", "Deve avere almeno 8 caratteri.");
			return "/users/update-password";
		}
		
		userService.updatePassword(userService.getById(currentUser.getId()), passwordEncoder.encode(newPassword));
		redirectAttributes.addFlashAttribute("alertMessage", "Password aggiornata con successo.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/users/profile";
	}
	
	// CREATE new user with role Operator
	@GetMapping("/create")
	public String create(Model model) {
		
		model.addAttribute("newOperator", new User() );
		
		return "/users/create";
	}
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("newOperator") User formOperator,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes) {
		
		if ( bindingResult.hasFieldErrors("email") || bindingResult.hasFieldErrors("password") || bindingResult.hasFieldErrors("firstName") || bindingResult.hasFieldErrors("lastName") ) {
			return "/users/create";
		}
		  
		// set encoded password
		formOperator.setPassword(  passwordEncoder.encode( formOperator.getPassword() ) );
		
		try {
			
			userService.createNewOperator(formOperator);
			redirectAttributes.addFlashAttribute("alertMessage", "L'operatore '" + formOperator.getFirstName() + " " + formOperator.getLastName() + "' è stato inserito correttamente.");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/users";
			
		} catch( DataIntegrityViolationException ex ) {
			
			bindingResult.rejectValue("email" , "error.newOperator", "Email già in uso da un altro account.");
			return "/users/create";
			
		}
		
	}
}
