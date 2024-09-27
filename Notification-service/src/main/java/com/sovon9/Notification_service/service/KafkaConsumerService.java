package com.sovon9.Notification_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.sovon9.Notification_service.dto.GuestCommInfo;

@Service
public class KafkaConsumerService
{
	@Autowired
	private EmailService emailService;
	
	@KafkaListener(topics = "PMS2", groupId = "email")
	public void consume(GuestCommInfo commInfo)
	{
		System.out.println("Consuming message : "+commInfo.getEmail());
		emailService.sendEmail(commInfo);
	}
}
