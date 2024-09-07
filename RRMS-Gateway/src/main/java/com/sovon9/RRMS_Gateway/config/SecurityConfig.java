package com.sovon9.RRMS_Gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig
{
	
	@Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf().disable()  // Disable CSRF as we are using stateless JWT tokens
//            .authorizeExchange(auth->auth.pathMatchers("/signin","/login")
//            		.permitAll()
//            		)
//                .pathMatchers("/auth/**").permitAll()  // Permit authentication endpoints without JWT
//                .anyExchange().authenticated()  // Require authentication for all other endpoints
//            .and()
//            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())  // Ensure stateless session
            .build();
    }
	
}
