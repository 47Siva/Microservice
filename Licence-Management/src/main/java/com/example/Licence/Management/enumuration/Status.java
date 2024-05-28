package com.example.Licence.Management.enumuration;

import com.example.Licence.Management.common.StatusDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
@JsonDeserialize(using = StatusDeserializer.class)
public enum Status {

	ACTIVE ,  INACTIVE , PENDING , APPROVED , ISSUED , RENEWED ,REQUEST;
	
    @JsonCreator
    public static Status fromValue(String value) {
        return Status.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase();
    }
}
