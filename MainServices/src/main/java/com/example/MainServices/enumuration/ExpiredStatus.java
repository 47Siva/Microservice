package com.example.MainServices.enumuration;

import com.example.Licence.Management.common.ExpriedStatusDeserializer;
import com.example.Licence.Management.enumuration.Status;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ExpriedStatusDeserializer.class)
public enum ExpiredStatus {
	EXPIRED , EXPIREDSOON , NOT_EXPIRED ,ACTIVETED;
	
	
    @JsonCreator
    public static ExpiredStatus fromValue(String value) {
        return ExpiredStatus.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase();
    }
}
