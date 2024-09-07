package com.sovon9.RRMS_Gateway.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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
	        return Jwts.parser()
	                        .verifyWith((SecretKey) key())
	                .build().parseSignedClaims(token)
	                .getPayload().getSubject();
	    }
}
