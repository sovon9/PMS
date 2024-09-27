package com.sovon9.RRMS_Portal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.sovon9.RRMS_Portal.dto.Reservation;

@Service
public class DashBoardService
{
	Logger LOGGER = LoggerFactory.getLogger(DashBoardService.class);
	
	@Autowired
	RestTemplate restTemplate;
	@Value("${RES_SERVICE_URL}")
	private String RES_SERVICE_URL;
	
	//@Cacheable(value = "reservation", key = "#status")
	public Reservation[] fetchDashBoardDataForRes(String status, String jwtToken)
	{
		ResponseEntity<Reservation[]> responseEntity = null;
		Reservation[] reservations = {};
		try
		{
			 HttpHeaders headers = new HttpHeaders();
		        headers.set("Authorization", "Bearer " + jwtToken);
		    HttpEntity<?> httpEntity = new HttpEntity<>(headers);
			responseEntity= restTemplate.exchange(
					RES_SERVICE_URL+"reservaion/status/RES", HttpMethod.GET, httpEntity, Reservation[].class);
			if (responseEntity.getStatusCode() == HttpStatus.OK)
			{
				reservations = responseEntity.getBody();
			}
			else
			{
				LOGGER.error("Error while fetching dashboard data with Status : "+responseEntity.getStatusCode());
				//throw new RuntimeException("Error while fetching dashboard data");
			}
			//reservations = responseEntity.getBody();
		}
		catch (HttpClientErrorException.Unauthorized e)
		{
			LOGGER.error("Unauthorized error while fetching dashboard data: " + e.getMessage());
			// Handle 401 Unauthorized error specifically
		}
		catch (ResourceAccessException | HttpServerErrorException e) // checking for both bot available[reservation, redis service
		{
			LOGGER.error("Error while fetching dashboard data");
		}
		return reservations;
	}
	
}
