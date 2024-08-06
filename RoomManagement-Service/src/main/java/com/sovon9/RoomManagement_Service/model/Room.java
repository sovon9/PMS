package com.sovon9.RoomManagement_Service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="room")
public class Room {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int roomNum;
	@Column
	private String roomStatus;
	@Column
	private Long guestID;
	@Column
	private String ratePlan;
	@Column
	private String roomDesc;
	@Column
	private int floor;
	public Room() {
		super();
	}
	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	public String getRoomStatus() {
		return roomStatus;
	}
	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}
	public String getDesc() {
		return roomDesc;
	}
	public void setDesc(String desc) {
		this.roomDesc = desc;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public Long getGuestID() {
		return guestID;
	}
	public void setGuestID(Long guestID) {
		this.guestID = guestID;
	}
	public String getRatePlan() {
		return ratePlan;
	}
	public void setRatePlan(String ratePlan) {
		this.ratePlan = ratePlan;
	}
	
}
