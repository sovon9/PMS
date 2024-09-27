package com.sovon9.Notification_service.service;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sovon9.Notification_service.dto.GuestCommInfo;

public class GuestCommInfoDeserializer implements Deserializer<GuestCommInfo>
{
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public GuestCommInfo deserialize(String topic, byte[] data)
	{
		try
		{
			return objectMapper.readValue(data, GuestCommInfo.class);
		}
		catch (Exception e) {
			throw new RuntimeException("Failed to deserialize JSON", e);
		}

	}

}
