package com.example.MainServices.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LicenceKeyDto {

	private String licenceKey;
	
	private String secretKey;
	
	   // Required for Jackson deserialization
    @JsonCreator
    public LicenceKeyDto(@JsonProperty("licenceKey") String licenceKey, @JsonProperty("secretKey") String secretKey) {
        this.licenceKey = licenceKey;
        this.secretKey = secretKey;
    }
}
