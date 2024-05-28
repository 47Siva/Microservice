package com.example.MainServices.config;

import com.example.MainServices.enumuration.Status;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class StatusDeserializer extends JsonDeserializer<Status> {

	@Override
	public Status deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String value = p.getText().toUpperCase();
		return Status.valueOf(value);
	}
}
