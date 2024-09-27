package com.sovon9.RRMS_Portal.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.sovon9.RRMS_Portal.constants.ResConstants;
import com.sovon9.RRMS_Portal.dto.GuestDto;
import com.sovon9.RRMS_Portal.dto.Reservation;
import com.sovon9.RRMS_Portal.dto.ReservationSearchDTO;
import com.sovon9.RRMS_Portal.dto.RoomDto;
import com.sovon9.RRMS_Portal.service.DashBoardService;
import com.sovon9.RRMS_Portal.service.ExtractJwtTokenFromCookie;
import com.sovon9.RRMS_Portal.service.ReservationService;
import com.sovon9.RRMS_Portal.service.RoomsService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/portal")
public class RRMSPortalController extends BaseController{

	@Value("${RES_SERVICE_URL}")
	private String RES_SERVICE_URL;
	@Value("${GUESTINFO_SERVICE_URL}")
	private String GUESTINFO_SERVICE_URL;

	Logger LOGGER = LoggerFactory.getLogger(RRMSPortalController.class);
	
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	DashBoardService dashBoardService;
	@Autowired
	RoomsService roomsService;
	@Autowired
	ReservationService reservationService;
	@Autowired
	private ExtractJwtTokenFromCookie jwtTokenFromCookie;
	
    @GetMapping("/home")
	public String home(Model model, HttpServletRequest request)
	{
    	String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
    	Reservation[] reservations = dashBoardService.fetchDashBoardDataForRes("RES", jwtToken);
		model.addAttribute("reservations", List.of(reservations));
		return "home";
	}
    
    @GetMapping("/create-res")
	public String createRes(Model model, HttpServletRequest request)
	{
		Reservation reservation = new Reservation();
		reservation.setStatus(ResConstants.RES_STATUS);
		//  Rateplan changes
		//List<RatePlanRoomMappingDto> ratePlans = List.of(new RatePlanRoomMappingDto(1,"12REGA"));
		String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
		List<RoomDto> allRateplanRoomData = roomsService.getAllRateplanRoomData(jwtToken);
	    model.addAttribute("ratePlans", allRateplanRoomData);
		//
		model.addAttribute("res", reservation);
		return "createReservation";
	}
    
    @PostMapping("/create-res")
	public String createReservation(@ModelAttribute("res") Reservation res, Model model, HttpServletRequest request)
	{
    	try
		{
			if (null == res)
			{
				throw new RuntimeException("Error while saving the reservation");
			}
			// getting JWT token from cookie
			String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			headers.set("Authorization", "Bearer " + jwtToken); // adding JWT token to header
			//
			if (null!= res && null == res.getGuestID())
			{
				
				ResponseEntity<GuestDto> entity = reservationService.createGuestInfo(res, headers);
				if (entity.getStatusCode() == HttpStatus.OK)
				{
					GuestDto guest = entity.getBody();
					res.setGuestID(guest.getGuestID());
				}
				else
				{
					// Handle non-200 response codes
					model.addAttribute("error", "Reservation failed with status: " + entity.getStatusCode());
					return "createReservation";
				}
				
			}
			
			// setting Reservation status to RES if not set for create reservation
			if(null==res.getStatus())
			{
				res.setStatus(ResConstants.RES_STATUS);
			}
			// setting create date
			res.setCreateDate(LocalDate.now());
			
			ResponseEntity<Reservation> responseEntity = reservationService.createReservation(res, headers);
			// Check response status code
			if (responseEntity.getStatusCode() == HttpStatus.OK)
			{
				Reservation reservation = responseEntity.getBody();
				model.addAttribute("success", "Reservation saved successfully!");
				model.addAttribute("reservation", reservation);
				return "createReservation";

			}
			else
			{

				model.addAttribute("error", "Reservation failed with status: " + responseEntity.getStatusCode());
			}
			
		}
    	catch (HttpStatusCodeException e)
		{
    		// Handle HTTP errors (4xx and 5xx)
    		model.addAttribute("error", "Reservation failed with error: " + e.getResponseBodyAsString());
		}
    	catch (Exception e)
		{
    		// Handle any other exceptions
            model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
		}
    	return "createReservation";
	}
    
    /////////////////////////  modify res ///////////////////////
    @GetMapping("/modify-res")
   	public String modifyResPage(Model model)
   	{
       model.addAttribute("res",new Reservation());
   	   return "modifyReservation";
   	}
    @PostMapping("/modify-res")
   	public String modifyReservation(@ModelAttribute("res") Reservation model)
   	{
    	if(null==model)
    	{
    		throw new RuntimeException("Error while saving the reservation");
    	}
    	// setting Reservation status as RES for create reservation
    	model.setStatus(ResConstants.REG_STATUS);
    	// setting the header values
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

		HttpEntity<Reservation> requestEntity = new HttpEntity<Reservation>(model, headers);

    	ResponseEntity<Reservation> responseEntity = restTemplate.exchange(RES_SERVICE_URL+"reservaion", HttpMethod.POST,requestEntity, Reservation.class);
    	// Check response status code
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
        	LOGGER.error("........................."+responseEntity.getBody());
        	return "saveReservation";
        	
        } else {
        	throw new RuntimeException("Error while saving the reservation"+responseEntity.getStatusCode());
        }
   	}

    //////////////////////////  search reservation   /////////////////////////
    @GetMapping({"/search-res","/search-res/{search}"})
	public String searchReservation(@ModelAttribute("res") ReservationSearchDTO res,
			@PathVariable(name = "search", required = false) boolean search, Model model, HttpServletRequest request)
	{
       LOGGER.error("===>  "+search);
       String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
       if(search)
       {
    	   String url = UriComponentsBuilder.fromHttpUrl(RES_SERVICE_URL+"search-reservaion?")
    	   .queryParam("firstName", res.getFirstName().trim())
    	   .queryParam("lastName", res.getLastName().trim())
    	   .queryParam("status", res.getStatus())
    	   .queryParam("arriveDate", res.getArriveDate())
    	   .queryParam("arriveTime", res.getArriveTime())
    	   .queryParam("deptDate", res.getDeptDate())
    	   .queryParam("deptTime", res.getDeptTime())
    	   .queryParam("roomnum", res.getRoomnum())
    	   .queryParam("guestID", res.getGuestID())
    	   .toUriString();
    	   
    	   HttpHeaders headers = new HttpHeaders();
           headers.set("Content-Type", "application/json");
           headers.set("Authorization", "Bearer " + jwtToken); // adding JWT token to header
           
    	   //HttpEntity<ReservationSearchDTO> requestBody = new HttpEntity<>(res, headers);
           LOGGER.error("url==> "+url);
    	   ResponseEntity<Reservation[]> responseEntity = reservationService.searchReservation(url, headers);
    	   if (responseEntity.getStatusCode() == HttpStatus.OK)
    	   {
    		   LOGGER.error("====>  "+List.of(responseEntity.getBody()));
    		   model.addAttribute("searchResult", null!=responseEntity.getBody()?List.of(responseEntity.getBody()):List.of());
    	   }
       }
   	   return "searchReservation";
   	}
    
    @GetMapping("/get-rooms-by-rateplan")
    @ResponseBody
    public List<Integer> getRoomsByRatePlan(@RequestParam("ratePlan") String ratePlan) {
        // Logic to fetch rooms based on the rate plan
        List<Integer> rooms = roomsService.getRoomsByRatePlan(ratePlan);
        return rooms;
    }


}
