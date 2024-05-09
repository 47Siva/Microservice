package com.example.Licence.Management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncryptDataDto {

	private String encrptData;
	
	private String secretKey;
}
