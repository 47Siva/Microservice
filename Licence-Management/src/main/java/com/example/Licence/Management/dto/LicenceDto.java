package com.example.Licence.Management.dto;

import java.util.UUID;

import com.example.Licence.Management.enumuration.ExpiredStatus;
import com.example.Licence.Management.enumuration.Status;

import lombok.Data;

@Data
public class LicenceDto {

	private UUID id;
	
	private String companyName;
	
	private String companyAddress;
	
	private long contactNumber;
	
	private String mailId;
	
	private String graceperiod;
	
	private String expiryDate;
	
	private Status status;
	
	private ExpiredStatus expiredStatus;
	
	private String activeationDate ;
	
	private String licenceKey;
}
