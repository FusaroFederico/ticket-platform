package com.platform.ticket.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.platform.ticket.spring.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	// INDEX
	@GetMapping
	public String index(Model model) {
		
		model.addAttribute("operators", userService.findAll());
		return "/users/index";
	}
	
	// SHOW
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer id, Model model) {
		
		
		model.addAttribute("operator", userService.getById(id));
		return "/users/show";
	}
	
}
