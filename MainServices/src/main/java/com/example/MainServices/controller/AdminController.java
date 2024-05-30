package com.example.MainServices.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MainServices.dto.EncryptDataDto;
import com.example.MainServices.service.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin_Controller")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	//return string
	@Operation(description = "GET End Point", 
			   summary = "This is a Get Licence List (response is String format) API"
	)
	@GetMapping("grtlistlicence")
	public ResponseEntity<?> getListLicence(){

		return adminService.getListLicence();
	}
	
    // using rest templet
	@Operation(description = "GET End Point", 
			   summary = "This is a Get Licence List (DTO format) API"
	)
	@GetMapping("/getlistoflicence")
	public ResponseEntity<?> getListOfLicence(){
		return adminService.getListOfLicence();
	}
	
	//using rest templet
	@Operation(description = "GET End Point", 
			   summary = "This is a Get Licence List API"
	)
	@GetMapping("/getalllicence")
	public ResponseEntity<?> getAllLicence(){
		return adminService.getAllLicence();
	}
	
	//using rest templet
	@Operation(description = "GET End Point", 
			   summary = "This is a Get Licence (Find By Id) API"
	)
	@GetMapping("/getlicence/{id}")
	public ResponseEntity<?> getByLicenceId(@PathVariable ("id") UUID id){		
		return adminService.getByLicenceId(id);
	}
	
	//return string
	//using rest templet
	@Operation(description = "GET End Point", 
			   summary = "This is a Get Licence (Find By Id , Response String Formate) API"
	)
	@GetMapping("/getlicencebyid/{id}")
	public ResponseEntity<?> getLicenceById(@PathVariable UUID id ) throws JsonMappingException, JsonProcessingException{
		return adminService.getbyLicenceId(id);
	}
	

	// get encrypt licenckey
	//using rest templet
	@Operation(description = "GET End Point", 
			   summary = "This is a Get Encrypt Licence Key  API"
	)
	@GetMapping("/getenencryptlicencekey/{id}")
	public ResponseEntity<?> getEncryptLicence(@PathVariable ("id") UUID id) throws JsonMappingException, JsonProcessingException{
		return adminService.getEncryptLicence(id);
	}
	
	//get encrypt data
	@Operation(description = "GET End Point", 
			   summary = "This is a Get Encrypt Data And Send Mail API"
	)
	@GetMapping("/getencryptdata/{id}")
	public ResponseEntity<?> getEncryptLData(
			@PathVariable UUID id, @RequestParam String toemail,
			@RequestParam String subject) throws JsonMappingException, JsonProcessingException {
		return adminService.getEncryptData(id,toemail,subject);
	}
	
	//get and update decryptdata
	@Operation(description = "POST End Point", 
			   summary = "This is a Get Decrypt Data API"
	)
	@PostMapping("/getdecryptdata")
	public ResponseEntity<?> decryptData(@RequestBody EncryptDataDto dataDto) throws Exception{
		return adminService.decryptData(dataDto);
	}

	//update decryptdata
	@Operation(description = "PUT End Point", 
			   summary = "This is a Licence Update API"
	)
	@PutMapping("/licenceupdate")
	public ResponseEntity<?> updatelicence(@RequestParam ("id") UUID id,
            @RequestParam ("licencekey") String licencekey) throws JsonMappingException, JsonProcessingException{
		return adminService.Update(id,licencekey);
	}
}
