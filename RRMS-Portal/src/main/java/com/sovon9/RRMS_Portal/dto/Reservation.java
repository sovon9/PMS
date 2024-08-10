package com.sovon9.RRMS_Portal.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class Reservation {

	private Long rrID;
	private Long resID;
	private Long guestID;
	private String firstName;
	private String lastName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate createDate;
	private String status;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate arriveDate;
	private LocalTime arriveTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate deptDate;
	private LocalTime deptTime;
	private Integer roomnum;

	public Reservation() {
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
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getRoomnum()
	{
		return roomnum;
	}
	public void setRoomnum(Integer roomnum)
	{
		this.roomnum = roomnum;
	}
	public LocalDate getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(LocalDate createDate)
	{
		this.createDate = createDate;
	}
	public LocalDate getArriveDate()
	{
		return arriveDate;
	}
	public void setArriveDate(LocalDate arriveDate)
	{
		this.arriveDate = arriveDate;
	}
	public LocalTime getArriveTime()
	{
		return arriveTime;
	}
	public void setArriveTime(LocalTime arriveTime)
	{
		this.arriveTime = arriveTime;
	}
	public LocalDate getDeptDate()
	{
		return deptDate;
	}
	public void setDeptDate(LocalDate deptDate)
	{
		this.deptDate = deptDate;
	}
	public LocalTime getDeptTime()
	{
		return deptTime;
	}
	public void setDeptTime(LocalTime deptTime)
	{
		this.deptTime = deptTime;
	}
	
}
