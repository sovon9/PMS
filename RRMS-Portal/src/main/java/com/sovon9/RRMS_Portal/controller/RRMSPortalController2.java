package com.sovon9.RRMS_Portal.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.sovon9.RRMS_Portal.constants.ResConstants;
import com.sovon9.RRMS_Portal.dto.Reservation;
import com.sovon9.RRMS_Portal.dto.ReservationSearchDTO;

@Controller
@RequestMapping("/")
public class RRMSPortalController2 extends BaseController{

	Logger LOGGER = LoggerFactory.getLogger(RRMSPortalController2.class);
	
	@Autowired
	RestTemplate restTemplate;
	
    @GetMapping("/home")
	public String home(Model model)
	{
    	ResponseEntity<Reservation[]> responseEntity = null;
    	Reservation[] reservations = {};
		try
		{
			responseEntity= restTemplate.exchange(
				"http://localhost:8081/res-service/reservaion/status/RES", HttpMethod.GET, null, Reservation[].class);
			if (responseEntity.getStatusCode() != HttpStatus.OK)
			{
				throw new RuntimeException("Error while fetching dashboard data");
			}
			reservations = responseEntity.getBody();
		}
		catch (ResourceAccessException e) {
			LOGGER.error("Error while fetching dashboard data");
		}
    	
		model.addAttribute("reservations", List.of(reservations));
		//model.addAttribute("currentDateTime", LocalDate.now());
		//model.addAttribute("agentName", "1200");
		return "home";
	}
    
    @GetMapping("/create-res")
	public String createRes(Model model)
	{
       model.addAttribute("res",new Reservation());
	   return "createReservation";
	}
    
    @PostMapping("/create-res")
	public String createReservation(@ModelAttribute("res") Reservation model)
	{
    	if(null==model)
    	{
    		throw new RuntimeException("Error while saving the reservation");
    	}
    	// setting Reservation status as RES for create reservation
    	model.setStatus(ResConstants.RES_STATUS);
    	// setting the header values
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        
        HttpEntity<Reservation> requestEntity =  new HttpEntity<Reservation>(model, headers);
        
    	ResponseEntity<Reservation> responseEntity = restTemplate.exchange("http://localhost:8081/res-service/reservaion", HttpMethod.POST,requestEntity, Reservation.class);
    	// Check response status code
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
        	LOGGER.error("........................."+responseEntity.getBody());
        	return "saveReservation";
        	
        } else {
        	throw new RuntimeException("Error while saving the reservation"+responseEntity.getStatusCode());
        }
	}
    
//    @GetMapping("/search-res")
//   	public String searchReservationPage(Model model)
//   	{
//       model.addAttribute("res",new Reservation());
//   	   return "searchReservation";
//   	}
    
    @GetMapping({"/search-res","/search-res/{search}"})
   	public String searchReservation(@ModelAttribute("res") ReservationSearchDTO res, @PathVariable(name="search", required = false) boolean search, Model model)
   	{
       LOGGER.error("===>  "+search);
       if(search)
       {
    	   String url = "http://localhost:8081/res-service/search-reservaion?" +
    	            "firstName=" + res.getFirstName() +
    	            "&lastName=" + res.getLastName() +
    	            "&status=" + res.getStatus() +
    	            "&arriveDate=" + res.getArriveDate() +
    	            "&arriveTime=" + res.getArriveTime() +
    	            "&deptDate=" + res.getDeptDate() +
    	            "&deptTime=" + res.getDeptTime() +
    	            "&roomnum=" + res.getRoomnum() +
    	            "&guestID=" + res.getGuestID();
    	   
    	   HttpHeaders headers = new HttpHeaders();
           headers.set("Content-Type", "application/json");
    	   HttpEntity<ReservationSearchDTO> requestBody = new HttpEntity<>(res, headers);
    	   ResponseEntity<Reservation[]> responseEntity = restTemplate.getForEntity(url, Reservation[].class);
    	   if (responseEntity.getStatusCode() == HttpStatus.OK)
    	   {
    		   LOGGER.error("====>  "+List.of(responseEntity.getBody()));
    		   model.addAttribute("searchResult", null!=responseEntity.getBody()?List.of(responseEntity.getBody()):List.of());
    	   }
       }
   	   return "searchReservation";
   	}
    

}
