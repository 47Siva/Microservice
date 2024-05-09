package com.example.Licence.Management.dto;

import java.io.Serializable;

import com.example.Licence.Management.enumuration.Status;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LicenceResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String companyName;
	
	private String companyAddress;
	
	private long contactNumber;
	
	private String mailId;
	
	private Status status;
	
	private String licenceKey;
	
}
