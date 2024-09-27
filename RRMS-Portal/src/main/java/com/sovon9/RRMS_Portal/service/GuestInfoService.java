package com.sovon9.RRMS_Portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sovon9.RRMS_Portal.dto.GuestDto;

@Service
public class GuestInfoService
{
	@Autowired
	RestTemplate restTemplate;
	
	public ResponseEntity<GuestDto[]> searchGuestData(String url, HttpHeaders headers)
	{
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.GET, httpEntity, GuestDto[].class);
	}
	
	public ResponseEntity<GuestDto> saveGuestData(String url,GuestDto guest, HttpHeaders headers)
	{
		// create Guest entity
		HttpEntity<GuestDto> guestEntity = new HttpEntity<>(guest, headers);
		return restTemplate.exchange(url, HttpMethod.POST, guestEntity, GuestDto.class);
	}
}
