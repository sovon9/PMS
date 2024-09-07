package com.sovon9.RatePlan_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sovon9.RatePlan_service.model.RatePlan;
import com.sovon9.RatePlan_service.repository.RatePlanRepository;

import jakarta.transaction.Transactional;

@Service
public class RatePlanService
{
	@Autowired
	private RatePlanRepository repository;
	
	@Transactional
	public RatePlan saveRatePlanDetails(RatePlan ratePlan)
	{
		return repository.save(ratePlan);
	}
	
	public RatePlan getRatePlanDetailsForRatePlan(String ratePlanCode)
	{
		return repository.findByRatePlanCode(ratePlanCode);
	}
}
