package com.sovon9.RRMS_Portal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.sovon9.RRMS_Portal.dto.RoomDto;

@Service
public class RoomsService
{
	Logger LOGGER = LoggerFactory.getLogger(RoomsService.class);
	@Autowired
	private RestTemplate restTemplate;
	
	private List<RoomDto> roomDtos;
	
	@Value("${ROOM_MGMT_SERVICE_URL}")
	private String ROOM_MGMT_SERVICE_URL;
	
	public List<Integer> getRoomsByRatePlan(String ratePlan)
	{
		return roomDtos.stream().filter(rp->rp.getRatePlan().equals(ratePlan)).map(rp->rp.getRoomNum()).collect(Collectors.toList());
	}
	
	public List<RoomDto> getAllRateplanRoomData()
	{
		roomDtos = new ArrayList<>();
		try
		{
			ResponseEntity<RoomDto[]> responseEntity = restTemplate
					.getForEntity(ROOM_MGMT_SERVICE_URL + "getAllAvailableRooms", RoomDto[].class);
			if (responseEntity.getStatusCode() == HttpStatus.OK)
			{
				roomDtos = List.of(responseEntity.getBody());
				LOGGER.error("========> " + roomDtos);
			}
		}
		catch (ResourceAccessException e) {
			LOGGER.error("Error fetching RatePlan RoomData : "+e.getMessage());
		}
		return roomDtos;
	}
}
