//package com.example.Licence.Management.common;
//
//import java.io.IOException;
//
//import com.example.Licence.Management.enumuration.Status;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//
//public class StatusDeserializer extends JsonDeserializer<Status> {
//	@Override
//	public Status deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//		String value = p.getText().toUpperCase();
//		return Status.valueOf(value);
//	}
//}
