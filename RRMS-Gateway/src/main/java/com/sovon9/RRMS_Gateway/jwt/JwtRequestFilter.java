package com.sovon9.RRMS_Gateway.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.sovon9.RRMS_Gateway.constants.StringConstants;
import com.sovon9.RRMS_Gateway.exception.HeaderException;

@Component
public class JwtRequestFilter //extends AbstractGatewayFilterFactory<JwtRequestFilter.Config>
{
//	Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);
//	
//	@Autowired
//	private JwtUtils jwtUtils;
//	
//	 public JwtRequestFilter(JwtUtils jwtUtils) {
//	        super(Config.class);
//	        this.jwtUtils = jwtUtils;
//	    }
//	 
//	 public static class Config {
//	        // Configuration properties for the filter can be added here if needed
//	    }
//
//	@Override
//	public GatewayFilter apply(Config config)
//	{
//		return (exchange, chain) -> {
//			try
//			{
//				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
//				{
//					throw new HeaderException(StringConstants.HEADER_NOT_FOUND);
//				}
//				String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//				String jwtToken = null;
//				// JWT Token is in the form "Bearer token". Remove Bearer word and get only the
//				// Token
//				if (header != null && header.startsWith("Bearer "))
//				{
//					jwtToken  = header.substring(7); // Remove Bearer prefix
//				}
//				if(null!=jwtToken && jwtUtils.validateJWTToken(jwtToken))
//				{
//					String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
//					return chain.filter(exchange);  // Valid token, continue request
//				}
//				else
//				{
//					exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//	                return exchange.getResponse().setComplete();  // Invalid token
//				}
//			}
//			catch(Exception e)
//			{
//				// send a different response
//				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                return exchange.getResponse().setComplete();  // Invalid token
//			}
//		};
//	}
	 

}
