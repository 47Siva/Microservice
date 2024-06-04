package com.example.Licence.Management.dto;

import com.example.Licence.Management.entity.Otp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OtpResponse {

	private String message;
	private int otp;
}
