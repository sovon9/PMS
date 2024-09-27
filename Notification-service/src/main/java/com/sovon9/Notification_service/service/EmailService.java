package com.sovon9.Notification_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sovon9.Notification_service.dto.GuestCommInfo;

@Service
public class EmailService
{
	Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail(String to, String subject, String text)
	{
		try
		{
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(to);
			mailMessage.setSubject(subject);
			mailMessage.setText(text);
			javaMailSender.send(mailMessage);
			LOGGER.info("Mail sucessfully sent to : "+to);
		}
		catch (Exception e)
		{
			LOGGER.error("Error occured during sending mail : sendEmail() "+e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param guestCommInfo
	 */
	public void sendEmail(GuestCommInfo guestCommInfo)
	{
		try
		{
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(guestCommInfo.getEmail());
			mailMessage.setSubject("Resrvation Created Sucessfully!");
			mailMessage.setText("Hi "+guestCommInfo.getFirstName()+",\n"+"Reservation Sucessfully Created For RESID:");
			javaMailSender.send(mailMessage);
			LOGGER.info("Mail sucessfully sent to : "+guestCommInfo.getEmail());
		}
		catch (Exception e)
		{
			LOGGER.error("Error occured during sending mail : sendEmail() "+e.getMessage());
		}
	}
}
