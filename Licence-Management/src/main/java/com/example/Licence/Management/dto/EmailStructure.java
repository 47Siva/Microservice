package com.example.Licence.Management.dto;

import lombok.Data;

@Data
public class EmailStructure {

	private String toEmail;
	private String body;
	private String subject;
	private String attachment;
	private String cc;
	private String bcc; 
}
