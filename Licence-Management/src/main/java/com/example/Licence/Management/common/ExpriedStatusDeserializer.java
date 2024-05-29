//package com.example.Licence.Management.common;
//
//import java.io.IOException;
//
//import com.example.Licence.Management.enumuration.ExpiredStatus;
//import com.example.Licence.Management.enumuration.Status;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//
//public class ExpriedStatusDeserializer extends JsonDeserializer<ExpiredStatus> {
//	@Override
//	public ExpiredStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//		String value = p.getText().toUpperCase();
//		return ExpiredStatus.valueOf(value);
//	}
//}
