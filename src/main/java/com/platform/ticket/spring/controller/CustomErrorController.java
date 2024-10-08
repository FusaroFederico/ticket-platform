package com.platform.ticket.spring.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

	@GetMapping
	public String errorHandler(HttpServletRequest request, Model model) {
		
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	    
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	        	return "/pages/error-404";
	        	
	        } else if( statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value() ) {
	        	return "/pages/error-500";
	        	
	        } else if( statusCode == HttpStatus.FORBIDDEN.value() ) {
	        	return "/pages/error-403";
	        }
	    }
	    
		return "/pages/error";
	}
}
