package com.sovon9.RRMS_Portal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sovon9.RRMS_Portal.dto.Reservation;

@FeignClient(name = "reservation-service",url = "localhost:8081", path = "/res-service")
public interface ReservationProxy {

	@PostMapping("/reservaion")
	public Reservation saveReservation(@RequestBody Reservation resVO);
	
}
