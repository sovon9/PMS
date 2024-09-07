package com.sovon9.RRMS_Portal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sovon9.RRMS_Portal.dto.LoginRequest;
import com.sovon9.RRMS_Portal.jwt.JwtUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginLogoutController
{
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/login")
	public String login(Model model)
	{
		model.addAttribute("login", new LoginRequest());
		return "login";
	}
	
	@ResponseBody
	@PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // we can directly pass username as we only need username for JWT token creation
        String jwtToken = jwtUtils.generateToken(userDetails.getUsername(), userDetails.getAuthorities());

//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//
//        LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken);
        
     // Set the JWT in an HttpOnly cookie
        Cookie jwtCookie = new Cookie("jwtToken", jwtToken);
        jwtCookie.setHttpOnly(true); // Prevent access via JavaScript
        jwtCookie.setSecure(true);   // Ensure cookie is only sent over HTTPS
        jwtCookie.setPath("/");      // Set the path for the cookie
        jwtCookie.setMaxAge(24 * 60 * 60); // Token valid for 1 day

        response.addCookie(jwtCookie);

        return ResponseEntity.ok("Login successful");
    }
}
