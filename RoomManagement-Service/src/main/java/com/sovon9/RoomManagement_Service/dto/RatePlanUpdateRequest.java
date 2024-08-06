package com.sovon9.RoomManagement_Service.dto;

import java.util.List;

public class RatePlanUpdateRequest {

	private List<String> roomNumbers;
    private String newRatePlan;
	public List<String> getRoomNumbers() {
		return roomNumbers;
	}
	public void setRoomNumbers(List<String> roomNumbers) {
		this.roomNumbers = roomNumbers;
	}
	public String getNewRatePlan() {
		return newRatePlan;
	}
	public void setNewRatePlan(String newRatePlan) {
		this.newRatePlan = newRatePlan;
	}
	
}
