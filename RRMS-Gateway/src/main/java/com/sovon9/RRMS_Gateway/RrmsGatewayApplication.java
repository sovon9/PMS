package com.sovon9.RRMS_Gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RrmsGatewayApplication{

	public static void main(String[] args) {
		try
		{
			SpringApplication.run(RrmsGatewayApplication.class, args);
		}
		catch (Exception e)
		{
			if (e.getClass().getName().equals("SilentExitException"))
			{
				// don't do anything
			}
		}
	}

}
