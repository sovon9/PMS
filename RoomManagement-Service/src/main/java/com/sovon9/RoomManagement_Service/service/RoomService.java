package com.sovon9.RoomManagement_Service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sovon9.RoomManagement_Service.model.Room;
import com.sovon9.RoomManagement_Service.repository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	private RoomRepository repository;
	
	public Room saveRoom(Room room)
	{
		return repository.save(room);
	}
	
	public Optional<Room> getRoom(Integer roomnum)
	{
		return repository.findById(roomnum);
	}
	
	public List<Room> getAllRoomDetails()
	{
		return repository.findAll();
	}
	
}
