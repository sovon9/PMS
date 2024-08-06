package com.sovon9.Reservation_service.repository;

import java.util.List;

import com.sovon9.Reservation_service.dto.ReservationSearchDTO;
import com.sovon9.Reservation_service.model.ReservationVO;

public interface ReservationRepositoryCustom{

	public List<ReservationVO> findReservations(ReservationSearchDTO searchDTO);
	
}
