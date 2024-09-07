package com.sovon9.RoomManagement_Service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sovon9.RoomManagement_Service.model.Room;
import com.sovon9.RoomManagement_Service.service.RoomService;

@RequestMapping("/room-service")
@RestController
public class RoomController {

	@Autowired
	private RoomService roomService;

	@PutMapping("/room/{roomnum}/status/{status}")
	public Room upateRoomStatus(@PathVariable("roomnum") Integer roomnum, @PathVariable("status") String status)
	{
		Room room = null;
		Optional<Room> roomData = roomService.getRoom(roomnum);
		if(roomData.isPresent())
		{
			room = roomData.get();
			room.setRoomStatus(status);
			room = roomService.saveRoom(room);
		}
		// to-do since it can return null make it optional
		return room;
	}
	
	@PutMapping("resID/{resID}/room/{roomnum}/status/{status}")
	public Room upateResRoomStatus(@PathVariable("resID") Long resID, @PathVariable("roomnum") Integer roomnum, @PathVariable("status") String status)
	{
		Room room = null;
		Optional<Room> roomData = roomService.getRoom(roomnum);
		if(roomData.isPresent())
		{
			room = roomData.get();
			room.setGuestID(resID);
			room.setRoomStatus(status);
			room = roomService.saveRoom(room);
		}
		// to-do since it can return null make it optional
		return room;
	}
	
	@GetMapping("/getAllAvailableRooms")
	public List<Room> getAllAvailableRooms()
	{
		return roomService.getAllRoomDetails();
	}
	
}
