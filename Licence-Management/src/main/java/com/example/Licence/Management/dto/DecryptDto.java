package com.example.Licence.Management.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DecryptDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mailId;
	
	private String licenceKey;
}
