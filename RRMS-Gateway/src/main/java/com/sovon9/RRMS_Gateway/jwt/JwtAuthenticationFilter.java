package com.sovon9.RRMS_Gateway.jwt;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.JwtException;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter
{
	
	@Value("${jwt.jwtSecret}")
	private String jwtSecret;
	
	@Value("${jwt.jwtExpiration}")
	private Long jwtExpiration;
	@Autowired
	private JwtUtils jwtUtils;
	
	 private static final List<String> EXCLUDED_PATHS = Arrays.asList(
		        "/login", 
		        "/signin"
		    );
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
	{
		String path = exchange.getRequest().getURI().getPath();
		// If the request is to one of the excluded paths, skip JWT validation
        if (EXCLUDED_PATHS.contains(path)) {
            return chain.filter(exchange);
        }
		
		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		String token = null;
		if (authHeader != null && authHeader.startsWith("Bearer "))
		{
			token = authHeader.substring(7);
		}
		else
		{
			// If Authorization header is not present, check for JWT token in cookies
	        HttpCookie jwtCookie = exchange.getRequest().getCookies().getFirst("jwtToken");
	        if (jwtCookie != null) {
	            // Extract token from the cookie
	            token = jwtCookie.getValue();
	        }
		}
		// If token is still null, return Unauthorized
        if(token==null)
        {
        	exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
         
        try {
            // Validate the JWT token
            // For example, using io.jsonwebtoken.Jwts
            //Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token);
        	if(null==token || !jwtUtils.validateJWTToken(token))
        	{
        		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();  // Invalid token
        	}
        } catch (JwtException e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

}
