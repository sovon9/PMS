package com.sovon9.RatePlan_service.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class RatePlan
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private String rateplanID;
	@Column(name="rateplan_code")
	private String ratePlanCode;
	@Column(name="single_rate")
	private BigDecimal singleRate;
	@Column(name="double_rate")
	private BigDecimal doubleRate;
	public RatePlan()
	{
		super();
	}
	public String getRateplanID()
	{
		return rateplanID;
	}
	public void setRateplanID(String rateplanID)
	{
		this.rateplanID = rateplanID;
	}
	public String getRatePlanCode()
	{
		return ratePlanCode;
	}
	public void setRatePlanCode(String ratePlanCode)
	{
		this.ratePlanCode = ratePlanCode;
	}
	public BigDecimal getSingleRate()
	{
		return singleRate;
	}
	public void setSingleRate(BigDecimal singleRate)
	{
		this.singleRate = singleRate;
	}
	public BigDecimal getDoubleRate()
	{
		return doubleRate;
	}
	public void setDoubleRate(BigDecimal doubleRate)
	{
		this.doubleRate = doubleRate;
	}
	
}
