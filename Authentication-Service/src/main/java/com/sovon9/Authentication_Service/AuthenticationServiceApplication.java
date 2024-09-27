package com.sovon9.Authentication_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthenticationServiceApplication {

	public static void main(String[] args) {
		try
		{
		SpringApplication.run(AuthenticationServiceApplication.class, args);
		}
		catch(Exception e)
		{
			if(e.getClass().getName().equals("SilentExitException"))
			{
				// pass
			}
		}
	}

}
