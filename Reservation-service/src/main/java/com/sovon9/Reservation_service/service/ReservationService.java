package com.sovon9.Reservation_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sovon9.Reservation_service.dto.ReservationSearchDTO;
import com.sovon9.Reservation_service.model.ReservationVO;
import com.sovon9.Reservation_service.repository.ReservationRepository;

/**
 * 
 * @author Sovon Singha
 *
 */
@Service
public class ReservationService {

	@Autowired
	private ReservationRepository repository;
	
	public ReservationVO saveReservationData(ReservationVO resVO)
	{
		return repository.save(resVO);
	}

	public List<ReservationVO> findReservationData(ReservationSearchDTO searchDTO) {
		return repository.findReservations(searchDTO);
	}

	public ReservationVO getResData(Long resID) {
		return repository.findById(resID).orElse(null);
	}
	
}
