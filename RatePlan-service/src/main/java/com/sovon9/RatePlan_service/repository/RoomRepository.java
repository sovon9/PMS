package com.sovon9.RatePlan_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sovon9.RatePlan_service.dto.RoomDto;

@Repository
public interface RoomRepository extends JpaRepository<RoomDto, Integer>{
	
}
