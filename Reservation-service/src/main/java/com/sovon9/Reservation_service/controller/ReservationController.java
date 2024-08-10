package com.sovon9.Reservation_service.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sovon9.Reservation_service.RoomProxy;
import com.sovon9.Reservation_service.model.ReservationVO;
import com.sovon9.Reservation_service.service.ReservationService;

@RestController
@RequestMapping("/res-service")
public class ReservationController {

	Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);
	
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
		if(null!=saveReservationData && null!=saveReservationData.getRoomnum())
		{
			roomProxy.upateResRoomStatus(saveReservationData.getResID(),saveReservationData.getRoomnum(), "VB"); // vacant blocked
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
		if(null!=saveReservationData && null!=saveReservationData.getRoomnum())
		{
			roomProxy.upateResRoomStatus(saveReservationData.getResID(),saveReservationData.getRoomnum(), "OC");
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
			roomProxy.upateRoomStatus(resVO.getRoomnum(), "VD");
			resVO = service.saveReservationData(resVO);
		}
		return resVO;
	}
	
//	@GetMapping("/search-reservaion")
//	public List<ReservationVO> findReservations(@RequestBody ReservationSearchDTO searchDTO)
//	{
//		return service.findReservationData(searchDTO);
//	}
	@GetMapping("/search-reservaion")
	public List<ReservationVO> findReservations(
		    @RequestParam(value = "firstName", required = false) String firstName,
		    @RequestParam(value = "lastName", required = false) String lastName,
		    @RequestParam(value = "status", required = false) String status,
		    @RequestParam(value = "arriveDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate arriveDate,
		    @RequestParam(value = "arriveTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalTime arriveTime,
		    @RequestParam(value = "deptDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deptDate,
		    @RequestParam(value = "deptTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalTime deptTime,
		    @RequestParam(value = "roomnum", required = false) Integer roomnum,
		    @RequestParam(value = "guestID", required = false) Long guestID)
	{
		Map<String,Object> queryParam = new HashMap<>();
		queryParam.put("firstName", firstName);
		queryParam.put("status", status);
		queryParam.put("arriveDate", arriveDate);
		queryParam.put("arriveTime", arriveTime);
		queryParam.put("deptDate", deptDate);
		queryParam.put("deptTime", deptTime);
		queryParam.put("roomnum", roomnum);
		queryParam.put("guestID", guestID);
		
		return service.findReservationData(queryParam);
	}
	
	@Cacheable(value = "reservaion", key = "#status")
	@GetMapping("/reservaion/status/{status}")
	public List<ReservationVO> getAllReservationByStatus(@PathVariable("status") String status)
	{
		LOGGER.error("===================Data Fetched From DB=======================");
		return service.fetchReservationData(status);
	}
	
}
