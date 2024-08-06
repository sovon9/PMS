package com.sovon9.Reservation_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.sovon9.Reservation_service.dto.RoomDto;


@FeignClient(name="RoomManagement-Service", path = "/room-service")
public interface RoomProxy {

	@PutMapping("/room/{roomnum}/status/{status}")
	public RoomDto upateRoomStatus(@PathVariable("roomnum") Integer roomnum, @PathVariable("status") String status);
	
	@PutMapping("resID/{resID}/room/{roomnum}/status/{status}")
	public RoomDto upateResRoomStatus(@PathVariable("resID") Long resID, @PathVariable("roomnum") Integer roomnum, @PathVariable("status") String status);
	
}
