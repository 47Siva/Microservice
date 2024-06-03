package com.example.Licence.Management.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ValidateOtpRequest {

	private String email;
	private int otp;
}
