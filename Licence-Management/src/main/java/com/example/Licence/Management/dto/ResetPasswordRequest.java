package com.example.Licence.Management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordRequest {

	   private String step;
	    private String email;
	    private String userName;
	    private int otp;
	    private String newPassword;
}
