package com.sovon9.Reservation_service.repository;

import java.util.ArrayList;
import java.util.List;

import com.sovon9.Reservation_service.dto.ReservationSearchDTO;
import com.sovon9.Reservation_service.model.ReservationVO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {

	@PersistenceContext // we can alo autowire
	private EntityManager entityManager;
	
	@Override
	public List<ReservationVO> findReservations(ReservationSearchDTO searchDTO) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ReservationVO> query = cb.createQuery(ReservationVO.class);
		/*
		 * normally we would have written query like select * from reservation where ? and ?.....
		 * so we will write the same way, first select * from reservation
		 */
		// select * from reservation
		Root<ReservationVO> root = query.from(ReservationVO.class);
		List<Predicate> predicates = new ArrayList<>();
		// where clause
		//where guestID = 
		if(null!=searchDTO.getGuestID())
		{
			Predicate guestID = cb.equal(root.get("guestID"), searchDTO.getGuestID());
			predicates.add(guestID);
		}
		//where createDate = 
		if(null!=searchDTO.getCreateDate())
		{
			Predicate createDate = cb.equal(root.get("createDate"), searchDTO.getCreateDate());
			predicates.add(createDate);
		}
		//where status = 
		if(null!=searchDTO.getStatus())
		{
			Predicate status = cb.equal(root.get("status"), searchDTO.getStatus());
			predicates.add(status);
		}
		//where status = 
		Predicate arriveDateTime = cb.equal(root.get("arriveDateTime"), searchDTO.getArriveDateTime());
		//where status = 
		Predicate deptDateTime = cb.equal(root.get("deptDateTime"), searchDTO.getDeptDateTime());
		// final query= select * from reservation where guestID =? and createDate =? and status=? and arriveDateTime=? and deptDateTime=?
		query.where(cb.and(predicates.toArray(Predicate[]::new)));
		TypedQuery<ReservationVO> typedQuery = entityManager.createQuery(query);
		return typedQuery.getResultList();
	}

}
