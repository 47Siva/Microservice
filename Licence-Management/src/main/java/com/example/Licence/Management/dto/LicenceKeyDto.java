package com.example.Licence.Management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LicenceKeyDto {

	private String licenceKey;
	
	private String secretKey;
}
