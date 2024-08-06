package com.sovon9.RatePlan_service.job;

import org.springframework.batch.item.ItemProcessor;

import com.sovon9.RatePlan_service.model.RatePlanRoomMapping;

public class RatePlanProcessor implements ItemProcessor<RatePlanRoomMapping, RatePlanRoomMapping>{

	@Override
	public RatePlanRoomMapping process(RatePlanRoomMapping item) throws Exception {
		System.out.println(item);
		return item;
	}

}
