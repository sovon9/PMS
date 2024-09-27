package com.sovon9.Authentication_Service.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sovon9.Authentication_Service.model.Role;
import com.sovon9.Authentication_Service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	@Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = :username")
	Optional<User> findByUsername(@Param("username") String username);
	@Query("SELECT r FROM Role r JOIN r.user u WHERE u.username = :username")
	Optional<Set<Role>> findRolesByUsername(@Param("username") String username);
}
