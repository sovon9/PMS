package com.sovon9.RatePlan_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sovon9.RatePlan_service.dto.RoomDto;
import com.sovon9.RatePlan_service.repository.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class RoomService {

	@Autowired
	private RoomRepository repository;
	
	@Transactional
	public RoomDto saveRoom(RoomDto room)
	{
		return repository.save(room);
	}
	
	public Optional<RoomDto> getRoom(Integer roomnum)
	{
		return repository.findById(roomnum);
	}
	
}
