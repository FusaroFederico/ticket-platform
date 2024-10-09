package com.platform.ticket.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
	
	@GetMapping
	public String homepage() {
		return "/pages/homepage";
	}
	
	// Get login form
	@GetMapping("login")
	public String loginForm() {
		return "/pages/login-form";
	}
	
	@GetMapping("logout")
	public String logout() {
		return "/pages/logout";
	}
}
