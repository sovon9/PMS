package com.sovon9.Reservation_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sovon9.Reservation_service.RoomProxy;
import com.sovon9.Reservation_service.dto.ReservationSearchDTO;
import com.sovon9.Reservation_service.model.ReservationVO;
import com.sovon9.Reservation_service.service.ReservationService;

@RestController
@RequestMapping("/res-service")
public class ReservationController {

	@Autowired
	private ReservationService service;
	@Autowired
	private RoomProxy roomProxy;
	
	/**
	 * 
	 * @param resVO
	 * @return
	 */
	@PostMapping("/reservaion")
	public ReservationVO saveReservation(@RequestBody ReservationVO resVO)
	{
		ReservationVO saveReservationData = service.saveReservationData(resVO);
		if(null!=saveReservationData && null!=saveReservationData.getRoonum())
		{
			roomProxy.upateResRoomStatus(saveReservationData.getResID(),saveReservationData.getRoonum(), "VB"); // vacant blocked
		}
		return saveReservationData;
	}
	
	/**
	 * 
	 * @param resVO
	 * @return
	 */
	@PutMapping("/reservaion/checkin")
	public ReservationVO checkInReservation(@RequestBody ReservationVO resVO)
	{
		resVO.setStatus("REG");
		ReservationVO saveReservationData = service.saveReservationData(resVO);
		if(null!=saveReservationData && null!=saveReservationData.getRoonum())
		{
			roomProxy.upateResRoomStatus(saveReservationData.getResID(),saveReservationData.getRoonum(), "OC");
		}
		else
		{
			// throw exception
		}
		return saveReservationData;
	}
	
	/**
	 * 
	 * @param resID
	 * @return null if no res found with resID
	 */
	@PutMapping("/reservaion/checkout/{resID}")
	public ReservationVO checkInReservation(@PathVariable("resID") Long resID)
	{
		ReservationVO resVO = service.getResData(resID);
		if (null != resVO) {
			resVO.setStatus("CO");
			roomProxy.upateRoomStatus(resVO.getRoonum(), "VD");
			resVO = service.saveReservationData(resVO);
		}
		return resVO;
	}
	
	@GetMapping("/search-reservaion")
	public List<ReservationVO> findReservations(@RequestBody ReservationSearchDTO searchDTO)
	{
		return service.findReservationData(searchDTO);
	}
	
}
