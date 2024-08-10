package com.sovon9.RRMS_Portal.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class BaseController
{
	@ModelAttribute
	public void addCommonAttributes(Model model)
	{
		// Get current date and time
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = currentDateTime.format(formatter);

		// Get logged-in agent name
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//String agentName = authentication.getName(); // This gets the username of the logged-in user
		String agentName = "1200";
		// Add to model
		model.addAttribute("currentDateTime", formattedDateTime);
		model.addAttribute("agentName", agentName);
	}
}
