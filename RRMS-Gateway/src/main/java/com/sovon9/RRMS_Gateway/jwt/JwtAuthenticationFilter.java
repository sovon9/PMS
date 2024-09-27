package com.sovon9.RRMS_Gateway.jwt;

import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.sovon9.RRMS_Gateway.config.SecureRouteValidator;

import io.jsonwebtoken.JwtException;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter
{
	Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	@Value("${jwt.jwtSecret}")
	private String jwtSecret;
	
	@Value("${jwt.jwtExpiration}")
	private Long jwtExpiration;
	@Autowired
	private JwtUtils jwtUtils;
//	@Value("${security.excluded.paths}")
//	private String[] excludedPaths;
	
	//private List<String> EXCLUDED_PATHS;
	@Autowired
	private SecureRouteValidator routeValidator;
	
//	@PostConstruct
//	public void getExcludedPaths()
//	{
//		EXCLUDED_PATHS = Arrays.asList(excludedPaths);
//	}
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
	{
		String path = exchange.getRequest().getURI().getPath();
		LOGGER.error("Path====> " + path);
		// If the request is to one of the excluded paths, skip JWT validation
		 if (routeValidator.isSecured.test(path)) {
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
//        	exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
        	LOGGER.error("Null Token Received from JwtAuthenticationFilter");
        	exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
    	    exchange.getResponse().getHeaders().set(HttpHeaders.LOCATION, "/unauthorized");
    	    return exchange.getResponse().setComplete();
        }
         
        try {
            // Validate the JWT token
            // For example, using io.jsonwebtoken.Jwts
        	if(!jwtUtils.validateJWTToken(token))
        	{
//        		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                return exchange.getResponse().setComplete();  // Invalid token
        		LOGGER.error("Null Token Received from JwtAuthenticationFilter");
            	exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
        	    exchange.getResponse().getHeaders().set(HttpHeaders.LOCATION, "/unauthorized");
        	    return exchange.getResponse().setComplete();
        	}
			// Extract username from token
			String username = jwtUtils.getUserNameFromJwtToken(token);
			Collection<? extends GrantedAuthority> authorities = jwtUtils.getAuthoritiesFromToken(token);
			// Check if the user has the ADMIN role
	        boolean isAdmin = authorities.stream()
	                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
			if (path.startsWith("/admin") && !isAdmin) {
			    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
			    return exchange.getResponse().setComplete();
			}
			
			// Create authentication object
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
					jwtUtils.getAuthoritiesFromToken(token));
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
			authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).forEach(System.out::println);
			// Modify the request to add the username to the headers for downstream services
			String roles = authorities.stream().map(auth->auth.getAuthority()).collect(Collectors.joining(","));
			ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
					.header("X-Username", username)
					.header("X-Roles", roles)
					.build();
			// Mutate the exchange with the modified request
			ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();

			return chain.filter(modifiedExchange);
        	
        } catch (JwtException e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

}
