package com.sovon9.RRMS_Portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sovon9.RRMS_Portal.ReservationProxy;
import com.sovon9.RRMS_Portal.dto.Reservation;

@Profile("old-controller")
@Controller
@RequestMapping("/")
public class RRMSPortalController {

	@Autowired
	ReservationProxy resproxy;
	
    @GetMapping("/home")
	public String home()
	{
	   return "home";
	}
    
    @GetMapping("/create-res")
	public String createRes(Model model)
	{
       model.addAttribute("res",new Reservation());
	   return "createReservation";
	}
    
    @PostMapping("/create-res")
	public String createRes(@ModelAttribute("res") Reservation model)
	{
       resproxy.saveReservation(model);
	   return "saveReservation";
	}
    
    @GetMapping("/search-res")
   	public String searchRes(Model model)
   	{
       model.addAttribute("res",new Reservation());
   	   return "searchReservation";
   	}

}
