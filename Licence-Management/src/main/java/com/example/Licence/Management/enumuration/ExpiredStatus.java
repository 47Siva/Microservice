package com.example.Licence.Management.enumuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExpiredStatus {
	EXPIRED , EXPIRED_SOON , NOT_EXPIRED , ACTIVETED;
	
    @JsonCreator
    public static Status fromValue(String value) {
        return Status.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase();
    }
}
