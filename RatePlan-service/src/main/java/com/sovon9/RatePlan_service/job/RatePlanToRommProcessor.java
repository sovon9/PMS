package com.sovon9.RatePlan_service.job;

import org.springframework.batch.item.ItemProcessor;

import com.sovon9.RatePlan_service.dto.RoomDto;
import com.sovon9.RatePlan_service.model.RatePlanRoomMapping;

public class RatePlanToRommProcessor implements ItemProcessor<RatePlanRoomMapping, RoomDto>{

	@Override
	public RoomDto process(RatePlanRoomMapping item) throws Exception {
		RoomDto dto = new RoomDto();
		dto.setRoomNum(item.getRoomnum());
		dto.setRatePlan(item.getRateplan());
		return dto;
	}

}
