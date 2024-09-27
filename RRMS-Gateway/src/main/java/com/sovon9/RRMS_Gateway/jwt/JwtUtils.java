package com.sovon9.RRMS_Gateway.jwt;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils
{
	Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${jwt.jwtSecret}")
	private String jwtSecret;
	
	@Value("${jwt.jwtExpiration}")
	private Long jwtExpiration;


	/**
	 * get the secret key
	 * @return
	 */
	private Key key()
	{
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	/**
	 * validate the JWT token on basis of secret key
	 * @param authToken
	 * @return
	 */
	public boolean validateJWTToken(String authToken)
	{
		  try {
	            System.out.println("Validating JWt Token");
	            //verifying with secrt key and parse it
	            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
	            return true;
	        } catch (MalformedJwtException e) {
	            LOGGER.error("Invalid JWT token: {}", e.getMessage());
	        } catch (ExpiredJwtException e) {
	            LOGGER.error("JWT token is expired: {}", e.getMessage());
	        } catch (UnsupportedJwtException e) {
	            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
	        } catch (IllegalArgumentException e) {
	            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
	        }
	        return false;
	}
	
	/**
	 * get userName from JWT Token
	 * @param token
	 * @return
	 */
	 public String getUserNameFromJwtToken(String token) {
		 return getClaimFromToken(token, Claims::getSubject); 
	    }
	 
	// Generic method to extract any claim from the JWT token
	    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }
	 
	 private Claims getAllClaimsFromToken(String token) {
	        return Jwts.parser()
	        		.verifyWith((SecretKey) key())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
	    }
	 
	 public Collection<? extends GrantedAuthority> getAuthoritiesFromToken(String token) {
	        Claims claims = getAllClaimsFromToken(token);

	        // Assuming the roles are stored in a claim called "roles" or "authorities"
	        Object rolesObject = claims.get("roles");
//	        if (rolesObject instanceof List) {
//	            String[] split = rolesObject.toString().split(",");
//	            return Arrays.stream(split)
//	                    .map(SimpleGrantedAuthority::new) // Convert role string into a GrantedAuthority
//	                    .collect(Collectors.toList());
//	        } else if (rolesObject instanceof Map) {
//	            // Handle cases where roles might be stored as a map
//	            Map<String, Object> rolesMap = (Map<String, Object>) rolesObject;
//	            return rolesMap.values().stream()
//	                    .map(Object::toString)
//	                    .map(SimpleGrantedAuthority::new)
//	                    .collect(Collectors.toList());
//
//	        }
	        if (rolesObject instanceof List) {
	            // Cast to List<String>
	            List<?> roles = (List<?>) rolesObject;

	            // Map each role to a GrantedAuthority
	            return roles.stream()
	            		.filter(LinkedHashMap.class::isInstance)
	            		.map(roleMap -> (LinkedHashMap<?, ?>) roleMap)
	                    .map(roleMap -> (String) roleMap.get("authority"))
	                    .map(SimpleGrantedAuthority::new)
	                    .collect(Collectors.toList());
	        }
			return Collections.emptyList();
	    }
	 
	 public List<String> getRolesFromJwtToken(String token) {
		    Claims claims = getAllClaimsFromToken(token);
		    Object roles = claims.get("roles");
		    if (roles instanceof List) {
		        // Ensure the list contains only strings
		        return ((List<?>) roles).stream()
		                .filter(role -> role instanceof String)
		                .map(role -> (String) role)
		                .collect(Collectors.toList());
		    }
		    return new ArrayList<>();
		}

}
