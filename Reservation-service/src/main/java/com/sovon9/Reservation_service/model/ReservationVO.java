package com.sovon9.Reservation_service.model;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="res")
public class ReservationVO {

	@Column
	private Long pmsID;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Long resID;
	@Column
	private Long guestID;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private Date createDate;
	@Column
	private String status;
	@Column
	private Date arriveDateTime;
	@Column
	private Date deptDateTime;
	@Column
	private String paymentType;
	@Column
	private Integer roonum;
	public ReservationVO() {
		super();
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
	public Long getRrID() {
		return pmsID;
	}
	public void setRrID(Long rrID) {
		this.pmsID = rrID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Long getPmsID() {
		return pmsID;
	}
	public void setPmsID(Long pmsID) {
		this.pmsID = pmsID;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public Integer getRoonum() {
		return roonum;
	}
	public void setRoonum(Integer roonum) {
		this.roonum = roonum;
	}
	@Override
	public int hashCode() {
		return Objects.hash(arriveDateTime, createDate, deptDateTime, firstName, guestID, lastName, paymentType, pmsID,
				resID, roonum, status);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReservationVO other = (ReservationVO) obj;
		return Objects.equals(arriveDateTime, other.arriveDateTime) && Objects.equals(createDate, other.createDate)
				&& Objects.equals(deptDateTime, other.deptDateTime) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(guestID, other.guestID) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(paymentType, other.paymentType) && Objects.equals(pmsID, other.pmsID)
				&& Objects.equals(resID, other.resID) && Objects.equals(roonum, other.roonum)
				&& Objects.equals(status, other.status);
	}
	
	
}
