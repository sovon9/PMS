package com.sovon9.Reservation_service.dto;

import java.util.Date;

public class ReservationSearchDTO {

	private Long rrID;
	private Long resID;
	private Long guestID;
	private Date createDate;
	private String status;
	private Date arriveDateTime;
	private Date deptDateTime;
	public ReservationSearchDTO() {
		super();
	}
	public Long getRrID() {
		return rrID;
	}
	public void setRrID(Long rrID) {
		this.rrID = rrID;
	}
	public Long getResID() {
		return resID;
	}
	public void setResID(Long resID) {
		this.resID = resID;
	}
	public Long getGuestID() {
		return guestID;
	}
	public void setGuestID(Long guestID) {
		this.guestID = guestID;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getArriveDateTime() {
		return arriveDateTime;
	}
	public void setArriveDateTime(Date arriveDateTime) {
		this.arriveDateTime = arriveDateTime;
	}
	public Date getDeptDateTime() {
		return deptDateTime;
	}
	public void setDeptDateTime(Date deptDateTime) {
		this.deptDateTime = deptDateTime;
	}
	
}
