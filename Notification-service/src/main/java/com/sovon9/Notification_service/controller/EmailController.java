package com.sovon9.Notification_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sovon9.Notification_service.service.EmailService;

@RestController
public class EmailController
{
	Logger LOGGER = LoggerFactory.getLogger(EmailController.class);
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/sendReservation-confirmation/to/{to}/name/{name}/resID/{resID}")
	public ResponseEntity<HttpStatus> sendReservationConfEmail(@PathVariable("to") String to,
			@PathVariable("name") String firstName, @PathVariable("name") Long resID)
	{
		emailService.sendEmail(to, "Reservation Confirmation", "Hi "+firstName+",\n"+resID+" has been Confirmed.");
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
}
