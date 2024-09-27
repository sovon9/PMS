package com.sovon9.Authentication_Service.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sovon9.Authentication_Service.dto.LoginRequest;
import com.sovon9.Authentication_Service.dto.LoginResponse;
import com.sovon9.Authentication_Service.dto.RegisterUser;
import com.sovon9.Authentication_Service.jwt.JwtUtils;
import com.sovon9.Authentication_Service.model.Role;
import com.sovon9.Authentication_Service.model.User;
import com.sovon9.Authentication_Service.service.RoleService;
import com.sovon9.Authentication_Service.service.UserService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController
{
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	 
	@PostMapping("/token")
	public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest)
	{
		try
		{
			//Authenticate the user based on credentials
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // we can directly pass username as we only need username for JWT token creation
            String jwtToken = jwtUtils.generateToken(userDetails.getUsername(), userDetails.getAuthorities());
            
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken);

            return ResponseEntity.ok(response);
		}
		catch (AuthenticationException e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	/**
	 * Registers a new user
	 * @param registerUser
	 * @return
	 */
	@PostMapping("/registerNewUser")
	public ResponseEntity<?> registerNewUser(@RequestBody RegisterUser registerUser)
	{
		if(null!=registerUser.getUsername() && null!=registerUser.getPassword())
		{
			User user = new User();
			user.setUsername(registerUser.getUsername());
			user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
			user.setEmail(registerUser.getEmail());
			user.setEnabled(true);
			//Set<Role> roles = new HashSet<>();
			//roles.add(roleService.findRoleDataWithRole("ROLE_ADMIN").get());
			Set<Role> roles = registerUser.getRole().stream().map(r->roleService.findRoleDataWithRole(r).orElse(null)).collect(Collectors.toSet());
			user.setRoles(roles);
			//saving new user to DB
			userService.saveNewUser(user);
			return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
}
