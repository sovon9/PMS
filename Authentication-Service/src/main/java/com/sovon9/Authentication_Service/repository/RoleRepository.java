package com.sovon9.Authentication_Service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sovon9.Authentication_Service.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
	Optional<Role> findByRole(String role);
}
