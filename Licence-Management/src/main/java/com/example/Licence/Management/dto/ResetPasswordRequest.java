package com.example.Licence.Management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordRequest {

	private String newpassword;
	
	private String email;
}
