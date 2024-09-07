package com.sovon9.RRMS_Portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig
{
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder)
	{
		UserDetails userDetails = User.withUsername("sovon").password(passwordEncoder.encode("password")).roles("ADMIN").build();
		return new InMemoryUserDetailsManager(userDetails);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers("/signin", "/login").permitAll()
				// .requestMatchers("/home").hasRole("USER")
				.anyRequest().authenticated())

				.exceptionHandling(exception -> exception.accessDeniedPage("/access-denied") // Custom access denied
																								// page for 403
						.authenticationEntryPoint((request, response, authException) -> {
							response.sendRedirect("/unauthorized"); // Redirect to custom unauthorized page for 401
						}))

				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf(csrf -> csrf.disable())
				// .formLogin(login->login.loginPage("/login").permitAll().defaultSuccessUrl("/home",true))
				.logout(logout -> logout.permitAll());
		return httpSecurity.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception
	{
		return builder.getAuthenticationManager();
	}
}
