package com.sovon9.RRMS_Gateway.config;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SecureRouteValidator
{
	@Value("${security.excluded.paths}")
	private String[] excludedPaths;
	
	private List<String> EXCLUDED_PATHS;
	
	@PostConstruct
	public void getExcludedPaths()
	{
		EXCLUDED_PATHS = Arrays.asList(excludedPaths);
	}
	
	public Predicate<String> isSecured = (path)->{
		return EXCLUDED_PATHS.stream().anyMatch(path::matches);
	};
}
