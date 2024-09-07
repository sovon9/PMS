package com.sovon9.RatePlan_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sovon9.RatePlan_service.model.RatePlan;

public interface RatePlanRepository extends JpaRepository<RatePlan, Integer>
{

	RatePlan findByRatePlanCode(String ratePlanCode);

}
