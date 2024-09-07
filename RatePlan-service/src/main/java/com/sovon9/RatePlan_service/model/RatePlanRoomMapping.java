package com.sovon9.RatePlan_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="rate_plan_room_mapping")
public class RatePlanRoomMapping {

	@Id
	@Column(name="room_num")
	private Integer roomnum;
	@Column(name="rate_plan")
	private String rateplan;
	public RatePlanRoomMapping() {
		super();
	}
	public Integer getRoomnum() {
		return roomnum;
	}
	public void setRoomnum(Integer roomnum) {
		this.roomnum = roomnum;
	}
	public String getRateplan() {
		return rateplan;
	}
	public void setRateplan(String rateplan) {
		this.rateplan = rateplan;
	}
	@Override
	public String toString() {
		return "RatePlanRoomMapping [roomnum=" + roomnum + ", rateplan=" + rateplan + "]";
	}
	
}
