package com.example.Licence.Management.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Licence.Management.dto.EncryptDataDto;
import com.example.Licence.Management.dto.LicenceKeyDto;
import com.example.Licence.Management.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/admin")
@Tag(name = "Admin_Controller")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	

	@Operation(description = "POST End Point", 
			   summary = "This is a Get Decrypt LicenceKey API"
	)
	@PostMapping("/getdecryptlicenceKey")
	public ResponseEntity<?> getdecryptlicencekey(@RequestBody LicenceKeyDto licenceKeyDto) throws Exception{
		return adminService.getDecryptlicencekey(licenceKeyDto);
	}
	

	@Operation(description = "POST End Point", 
			   summary = "This is a Get Decrypt Data API"
	)
	@PostMapping("/getdecryptdata")
	public ResponseEntity<?> decryptDataAndUpdateLicenceStatus(@RequestBody EncryptDataDto dataDto) throws Exception{
		return adminService.getDecryptDataAndUpdateLicenceStatus(dataDto);
	}
	

	@Operation(description = "PUT End Point", 
			   summary = "This is a Licence Update API"
	)
	@PutMapping("/updatelicence")
	public ResponseEntity<?> updateLicence(@RequestParam ("id") UUID id,
			                                @RequestParam ("licencekey") String licencekey){
		return adminService.updateLicence(id,licencekey);
	}
}
