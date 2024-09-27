package com.sovon9.Authentication_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sovon9.Authentication_Service.model.User;
import com.sovon9.Authentication_Service.repository.UserRepository;

@Service
public class UserService
{
	@Autowired
	private UserRepository userRepository;
	
	public User saveNewUser(User user)
	{
		return userRepository.save(user);
	}
}
