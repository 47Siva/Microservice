package com.example.Licence.Management.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Licence.Management.dto.EncryptDataDto;
import com.example.Licence.Management.dto.LicenceKeyDto;
import com.example.Licence.Management.dto.LicenceResponseDto;
import com.example.Licence.Management.service.LicenceService;



@RestController
@RequestMapping("api/licence")
public class LicenceController {

	@Autowired
	private LicenceService licenceService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createLicence(@RequestBody LicenceResponseDto licencedto){
		
		return licenceService.createLicence(licencedto);
	}
	
	@GetMapping("/getlicence/{id}")
	public ResponseEntity<?> getlicence(@PathVariable UUID id){
		return licenceService.getLicence(id);
	}
	
	@GetMapping("/generatelicencekey/{id}")
    public ResponseEntity<?> generateLicenceKey(@PathVariable UUID id) {
        return licenceService.generateLicenceKey(id);
    }
	
	@GetMapping("/getLicence/{id}")
	public ResponseEntity<?> getlicenceEntity(@PathVariable UUID id ){
		return licenceService.getLicenceEntity(id);
	}
	
	@GetMapping("/getenlicencekey/{id}")
	public ResponseEntity<?> getEncryptLicenceById(@PathVariable UUID id) throws Exception{
		return licenceService.getEncryptLicenceKey(id);
	}
	
	@GetMapping("/getencryptdata/{id}")
	public ResponseEntity<?> getencryptData (@PathVariable UUID id) throws Exception{
		return licenceService.getEncryptData(id);
	}
	
	
	

	
}
