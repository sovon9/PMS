package com.sovon9.Authentication_Service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sovon9.Authentication_Service.model.Role;
import com.sovon9.Authentication_Service.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Service
public class RoleService
{
	@Autowired
	private RoleRepository repository;
	
	@Transactional
	public Role saveRole(Role role)
	{
		return repository.save(role);
	}
	
	public Optional<Role> findRoleDataWithRole(String role)
	{
		return repository.findByRole(role);
	}
}
