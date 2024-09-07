package com.sovon9.RatePlan_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sovon9.RatePlan_service.model.RatePlanRoomMapping;

@Repository
public interface RatePlanRoomMappingRepository extends JpaRepository<RatePlanRoomMapping, Integer>{

	
	
}
